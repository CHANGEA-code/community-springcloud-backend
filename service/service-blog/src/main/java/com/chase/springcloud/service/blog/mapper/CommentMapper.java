package com.chase.springcloud.service.blog.mapper;

import com.chase.springcloud.service.blog.dto.resp.CommentRespDto;
import com.chase.springcloud.service.blog.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 评论表 Mapper 接口
 * </p>
 *
 * @author zebin
 * @since 2022-11-04
 */
@Repository
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    /**
     * 获取第一层评论
     * @param topicId
     * @return
     */
    List<CommentRespDto> getFirstLayerComments(@Param("topicId") String topicId);

    /**
     * 通过评论id获取其子评论
     * @param commentId
     * @return
     */
    List<CommentRespDto> getCommentsByCommentId(@Param("commentId") String commentId);
}
