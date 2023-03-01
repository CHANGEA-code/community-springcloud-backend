package com.chase.springcloud.service.blog.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chase.springcloud.service.blog.dto.resp.PostRespDto;
import com.chase.springcloud.service.blog.entity.Post;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * <p>
 * 话题表 Mapper 接口
 * </p>
 *
 * @author zebin
 * @since 2022-11-04
 */
@Repository
public interface PostMapper extends BaseMapper<Post> {


    List<PostRespDto> recommendPost(@Param("topicId")String topicId, @Param("tagId")String tagId, @Param("pageSize")Integer pageSize);

    int addZan(String id);

    int cancelZan(String id);

    int subtractComments(@Param("topicId") String topicId, @Param("count") Integer count);
}
