package com.chase.springcloud.service.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chase.springcloud.common.base.model.R;
import com.chase.springcloud.common.base.util.RedisCache;
import com.chase.springcloud.common.base.util.ThreadPoolUtils;
import com.chase.springcloud.service.base.model.User;
import com.chase.springcloud.service.blog.dto.req.PostReqDto;
import com.chase.springcloud.service.blog.dto.resp.PageRespDto;
import com.chase.springcloud.service.blog.entity.Comment;
import com.chase.springcloud.service.blog.entity.Post;
import com.chase.springcloud.service.blog.entity.Tag;
import com.chase.springcloud.service.blog.dto.resp.PostRespDto;
import com.chase.springcloud.service.blog.feign.OssFileService;
import com.chase.springcloud.service.blog.mapper.*;
import com.chase.springcloud.service.blog.service.PostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;

/**
 * <p>
 * 话题表 服务实现类
 * </p>
 *
 * @author zebin
 * @since 2022-11-04
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OssFileService ossFileService;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private RedisCache redisCache;
    private static ArrayList<String> postListCacheKeys = new ArrayList<>();
    static {
        postListCacheKeys.add("post:hot");
        postListCacheKeys.add("post:latest");
    }
    @Override
    public PageRespDto<PostRespDto> getPageList(PostReqDto postReqDto) {
        int pageNum = postReqDto.getPageNum(),pageSize = postReqDto.getPageSize();
        PageRespDto<PostRespDto> res = null;
        String hashKey = "post:"+postReqDto.getTab();
        String entryKey = pageNum+":"+pageSize;
        if (!StringUtils.isEmpty(postReqDto.getTab())){
            //先redis中查
            res = (PageRespDto<PostRespDto>)redisCache.getCacheMap(hashKey).get(entryKey);
        }
        if (res != null) return res;
        //若为空，则去数据库获取
        Page<Post> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Post> wrapper = new QueryWrapper<>();
        wrapper.likeRight(!StringUtils.isEmpty(postReqDto.getTitle()),"title", postReqDto.getTitle())
                .likeRight(!StringUtils.isEmpty(postReqDto.getContent()),"content", postReqDto.getContent())
                .ge(!StringUtils.isEmpty(postReqDto.getGmtCreateBegin()),"create_time", postReqDto.getGmtCreateBegin())
                .le(!StringUtils.isEmpty(postReqDto.getGmtCreateEnd()),"create_time", postReqDto.getGmtCreateEnd())
                .orderByDesc("latest".equals(postReqDto.getTab()),"create_time")
                .orderByDesc("hot".equals(postReqDto.getTab()),"view");
        Page<Post> resPage = baseMapper.selectPage(page, wrapper);
        List<PostRespDto> list = new ArrayList<>();
        //由于lambda表达式中不可改变外部作用域的变量，需要用for循环
        for (Post v : resPage.getRecords()){
            User user = userMapper.selectById(v.getAuthorId());
            String content = v.getContent();
            PostRespDto dto = PostRespDto.builder()
                    .id(v.getId())
                    .title(v.getTitle())
                    .author(user)
                    .cover(v.getCover())
                    .content(v.getContent().substring(0, content.length()>=200?200:content.length()))
                    .zans(v.getZans())
                    .view(v.getView())
                    .comments(v.getComments())
                    .createTime(v.getCreateTime())
                    .updateTime(v.getUpdateTime())
                    .build();
            list.add(dto);
        }
        PageRespDto<PostRespDto> respDto = PageRespDto.of(pageNum, pageSize, resPage.getTotal(),list);
        if (!StringUtils.isEmpty(postReqDto.getTab())) {
            //添加到redis中再返回
            redisCache.setCacheMapValue(hashKey, entryKey, respDto);
            redisCache.expire(hashKey,1L,TimeUnit.HOURS);
        }
        return respDto;
    }

    @Override
    public PageRespDto<PostRespDto> pageByUserId(Long id,PostReqDto postReqDto) {
        int pageNum = postReqDto.getPageNum(),pageSize = postReqDto.getPageSize();
        Page<Post> page = new Page<>(postReqDto.getPageNum(), postReqDto.getPageSize());
        QueryWrapper<Post> query = new QueryWrapper<>();
        query.eq("author_id",id).orderByDesc("create_time");
        Page<Post> postPage = baseMapper.selectPage(page, query);
        List<PostRespDto> list = new ArrayList<>();
        //由于lambda表达式中不可改变外部作用域的变量，需要用for循环
        for (Post v : postPage.getRecords()){
            User user = userMapper.selectById(v.getAuthorId());
            String content = v.getContent();
            PostRespDto dto = PostRespDto.builder()
                    .id(v.getId())
                    .title(v.getTitle())
                    .author(user)
                    .cover(v.getCover())
                    .content(v.getContent().substring(0, content.length()>=200?200:content.length()))
                    .zans(v.getZans())
                    .view(v.getView())
                    .comments(v.getComments())
                    .createTime(v.getCreateTime())
                    .updateTime(v.getUpdateTime())
                    .build();
            list.add(dto);
        }
        PageRespDto<PostRespDto> respDto = PageRespDto.of(pageNum, pageSize, postPage.getTotal(),list);
        return respDto;
    }
    @Override
    public PostRespDto getDetail(Long id) {
//        long timeMillis = System.currentTimeMillis();
//        System.out.println("耗时：" + (System.currentTimeMillis() - timeMillis));
        Post post = baseMapper.selectById(id);
        if (post == null) return null;
        //添加作者
        User user = userMapper.selectById(post.getAuthorId());
        if (user == null) return null;
        //多线程并发处理
        ExecutorService executor = ThreadPoolUtils.httpApiThreadPool;
        executor.execute(() -> {
            //更新阅读量
            post.setView(post.getView() + 1);
            baseMapper.updateById(post);
        });
        Future<String> submit = executor.submit(() -> {
            //获取标签字符串
            String tagName = tagMapper.getTagStr(post.getTagId());
            return tagName;
        });
        Future<String> submit2 = executor.submit(() -> {
            //获取父标签id
            return tagMapper.getParentIdById(post.getTagId());
        });
        try {
            return PostRespDto.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .author(user)
                    .cover(post.getCover())
                    .content(post.getContent())
                    .zans(post.getZans())
                    .tagId(post.getTagId())
                    .view(post.getView())
                    .comments(post.getComments())
                    .createTime(post.getCreateTime())
                    .updateTime(post.getUpdateTime())
                    .tagName(submit.get()) //阻塞获取标签字符串
                    .parentTagId(submit2.get())
                    .build();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PostRespDto> getPostByIdBatch(List<String> ids){
        if (ids == null || ids.size() <= 0) return null;
        List<Post> posts = baseMapper.selectBatchIds(ids);
        List<PostRespDto> list = new ArrayList<>();
        ExecutorService executor = ThreadPoolUtils.httpApiThreadPool;
        posts.stream().map(v->{
            //多线程并发处理
            executor.execute(() -> {
                //更新阅读量
                v.setView(v.getView() + 1);
                baseMapper.updateById(v);
            });
            Future<String> submit = executor.submit(() -> {
                //获取标签字符串
                String tagName = tagMapper.getTagStr(v.getTagId());
                return tagName;
            });
            Future<String> submit2 = executor.submit(() -> {
                //获取父标签id
                return tagMapper.getParentIdById(v.getTagId());
            });
            User user = userMapper.selectById(v.getAuthorId());
            PostRespDto dto = null;
            try {
                dto = PostRespDto.builder()
                        .id(v.getId())
                        .title(v.getTitle())
                        .author(user)
                        .cover(v.getCover())
                        .content(v.getContent())
                        .zans(v.getZans())
                        .tagId(v.getTagId())
                        .view(v.getView())
                        .comments(v.getComments())
                        .createTime(v.getCreateTime())
                        .updateTime(v.getUpdateTime())
                        .tagName(submit.get()) //阻塞获取标签字符串
                        .parentTagId(submit2.get())
                        .build();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
            list.add(dto);
            return dto;
        });
        return list;
    }

    @Override
    public List<PostRespDto> recommendPost(String topicId, String tagId, Integer pageSize) {
        return postMapper.recommendPost(topicId, tagId, pageSize);
    }

    @Override
    public String addOrEditPost(PostReqDto postReqDto) {
        Post post = Post.builder()
                .id(postReqDto.getId())
                .title(postReqDto.getTitle())
                .cover(postReqDto.getCover())
                .content(postReqDto.getContent())
                .tagId(postReqDto.getTagId())
                .authorId(postReqDto.getAuthorId())
                .build();
        Boolean res;
        if (!StringUtils.isEmpty(postReqDto.getId())){
            post.setId(postReqDto.getId());
            res =  baseMapper.updateById(post) > 0;
        }else {
            QueryWrapper<Post> wrapper = new QueryWrapper<>();
            wrapper.eq("title", postReqDto.getTitle());
            List<Post> list = postMapper.selectList(wrapper);
            //检查是否有标题重复
            if (list != null && list.size() > 0) return null;
            res = baseMapper.insert(post) > 0;
        }
        //删除缓存
        redisCache.deleteObject(postListCacheKeys);
        if (res) return post.getId();
        else return null;
    }
    @Override
    public boolean removePost(String id) {
        Post post = baseMapper.selectById(id);
        if (post == null) return false;
        //删除文章
        int i = baseMapper.deleteById(id);
        //删除封面
        ossFileService.removeFile(post.getCover());
        //删除评论
        QueryWrapper<Comment> query = new QueryWrapper<>();
        query.eq("topic_id", id);
        commentMapper.delete(query);
        //删除缓存
        redisCache.deleteObject(postListCacheKeys);
        //失败重试机制--待添加
        if (i <= 0) return false;
        else return true;
    }

    @Override
    public boolean removePostByIds(List<String> idList) {
        if (idList == null || idList.size() <= 0) return false;
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("cover").in("id", idList);
        List<Object> list = baseMapper.selectObjs(queryWrapper);
        List<String> urlList = new ArrayList<>();
        for (Object o : list){
            String s = (String)o;
            if (StringUtils.isEmpty(s)) continue;
            urlList.add(s);
        }
        int i = baseMapper.deleteBatchIds(idList);
        if (i <= 0) return false;
        //批量删除封面
        ossFileService.removeFileBatch(urlList);
        return true;
    }
    @Override
    public boolean addZan(String id) {
        if (postMapper.addZan(id) <= 0) return false;
        else return true;
    }
    @Override
    public boolean cancelZan(String id) {
        if (postMapper.cancelZan(id) <= 0) return false;
        else return true;
    }
}
