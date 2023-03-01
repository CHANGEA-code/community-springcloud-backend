package com.chase.springcloud.service.blog.mapper;

import com.chase.springcloud.service.blog.entity.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chase.springcloud.service.blog.dto.resp.TagRespDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 标签表 Mapper 接口
 * </p>
 *
 * @author zebin
 * @since 2022-11-04
 */
@Repository
public interface TagMapper extends BaseMapper<Tag> {
    String getTagStr(String tagId);
    List<TagRespDto> selectNestedListByParentId(String parentId);

    /**
     * 获取标签的父标签的id
     * @param tagId
     * @return
     */
    String getParentIdById(String tagId);
}
