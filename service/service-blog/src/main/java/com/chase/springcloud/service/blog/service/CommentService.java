package com.chase.springcloud.service.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chase.springcloud.service.blog.dto.resp.CommentRespDto;
import com.chase.springcloud.service.blog.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 评论表 服务类
 * </p>
 *
 * @author zebin
 * @since 2022-11-04
 */
public interface CommentService extends IService<Comment> {
    /**
     * 获取第一层评论
     * @param topicId
     * @return
     */
    List<CommentRespDto> getFirstLayerComments(String topicId);

    /**
     * 通过第一层评论id获取子评论
     * @param commentId
     * @return
     */
    List<CommentRespDto> getCommentsByCommentId(String commentId);
    boolean addComment(Comment comment);
    /**
     * 通过评论id删除评论以及子评论
     * @param commentId
     * @return
     */
    boolean deleteCommentAndChilds(String commentId);
    boolean updateComment(Comment comment);
}
