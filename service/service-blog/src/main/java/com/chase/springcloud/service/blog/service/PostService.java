package com.chase.springcloud.service.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chase.springcloud.service.blog.dto.req.PostReqDto;
import com.chase.springcloud.service.blog.dto.resp.PageRespDto;
import com.chase.springcloud.service.blog.entity.Post;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chase.springcloud.service.blog.dto.resp.PostRespDto;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * <p>
 * 话题表 服务类
 * </p>
 *
 * @author zebin
 * @since 2022-11-04
 */
public interface PostService extends IService<Post> {

    PageRespDto<PostRespDto> getPageList(PostReqDto postReqDto);

    PageRespDto<PostRespDto>  pageByUserId(Long id, PostReqDto postReqDto);

    PostRespDto getDetail(Long id);

    List<PostRespDto> getPostByIdBatch(List<String> ids);
    List<PostRespDto> recommendPost(String topicId,String tagId,Integer pageSize);

    String  addOrEditPost(PostReqDto postReqDto);

    boolean removePost(String id);

    boolean removePostByIds(List<String> idList);

    boolean addZan(String id);

    boolean cancelZan(String id);
}
