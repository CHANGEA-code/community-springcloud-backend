package com.chase.springcloud.service.blog.service;

import com.chase.springcloud.service.blog.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chase.springcloud.service.blog.dto.resp.TagRespDto;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 标签表 服务类
 * </p>
 *
 * @author zebin
 * @since 2022-11-04
 */
public interface TagService extends IService<Tag> {

    String getTagStr(String tagId);

    List<TagRespDto> nestedList();

    void batchImport(InputStream inputStream);

    boolean addOrEdit(Tag tag);

    boolean delete(Long id);
}
