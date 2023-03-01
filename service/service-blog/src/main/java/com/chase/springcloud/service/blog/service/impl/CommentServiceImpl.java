package com.chase.springcloud.service.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chase.springcloud.common.base.util.ThreadPoolUtils;
import com.chase.springcloud.service.base.model.User;
import com.chase.springcloud.service.blog.dto.resp.CommentRespDto;
import com.chase.springcloud.service.blog.entity.Comment;
import com.chase.springcloud.service.blog.entity.Post;
import com.chase.springcloud.service.blog.mapper.CommentMapper;
import com.chase.springcloud.service.blog.mapper.PostMapper;
import com.chase.springcloud.service.blog.mapper.UserMapper;
import com.chase.springcloud.service.blog.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author zebin
 * @since 2022-11-04
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean addComment(Comment comment) {
        int i = baseMapper.insert(comment);
        //添加统计数
        Post post = postMapper.selectById(comment.getTopicId());
        post.setComments(post.getComments() + 1);
        int i1 = postMapper.updateById(post);
        return i > 0 && i1 > 0;
    }
    @Override
    public boolean deleteCommentAndChilds(String commentId) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("id", commentId);
        wrapper.or().eq("reply_id", commentId);
        Integer count = commentMapper.selectCount(wrapper);
        Comment comment = commentMapper.selectById(commentId);
        //更新文章评论数
        int i1 = postMapper.subtractComments(comment.getTopicId(), count);
        //删除文章
        int i = commentMapper.delete(wrapper);
        return i > 0 && i1 > 0;
    }
    @Override
    public boolean updateComment(Comment comment) {
        if (StringUtils.isEmpty(comment.getId())) return false;
        int i = baseMapper.updateById(comment);
        return i > 0;
    }

    @Override
    public List<CommentRespDto> getFirstLayerComments(String topicId) {
        ExecutorService pool = ThreadPoolUtils.httpApiThreadPool;
        List<CommentRespDto> comments = commentMapper.getFirstLayerComments(topicId);
        return comments.stream().map(v->{
            Future<User> submit1 = pool.submit(() -> {
                //获取用户对象
                return userMapper.selectById(v.getUserId());
            });
            Future<List<CommentRespDto>> submit2 = pool.submit(() -> {
                //获取子评论列表
                return getCommentsByCommentId(v.getId());
            });
            try {
                User user = submit1.get();
                return CommentRespDto.builder()
                        .id(v.getId())
                        .content(v.getContent())
                        .replyId(v.getReplyId())
                        .replyName(v.getReplyName())
                        .topicId(v.getTopicId())
                        .userId(v.getUserId())
                        .userName(user.getUserName())
                        .emailHash(user.getEmailHash())
                        .createTime(v.getCreateTime())
                        .updateTime(v.getUpdateTime())
                        .childs(submit2.get())
                        .build();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }
    private List<CommentRespDto> commentsList;
    @Override
    public List<CommentRespDto> getCommentsByCommentId(String commentId) {
        commentsList = new ArrayList<>();
        getCommentsByDfs(commentId);
        return commentsList;
    }
    //添加事务
    /**
     * 递归获取子评论
     * @param commentId
     */
    private void getCommentsByDfs(String commentId) {
        List<CommentRespDto> comments = commentMapper.getCommentsByCommentId(commentId);
        if (comments != null && comments.size() > 0){
            for (CommentRespDto comment : comments){
                //添加进列表
                commentsList.add(comment);
                //递归
                getCommentsByDfs(comment.getId());
            }
        }
    }
}
