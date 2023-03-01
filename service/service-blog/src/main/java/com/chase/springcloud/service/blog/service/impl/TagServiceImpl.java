package com.chase.springcloud.service.blog.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.chase.springcloud.common.base.util.RedisCache;
import com.chase.springcloud.service.blog.entity.ExcelTagData;
import com.chase.springcloud.service.blog.entity.Tag;
import com.chase.springcloud.service.blog.dto.resp.TagRespDto;
import com.chase.springcloud.service.blog.listener.ExcelTagDataListener;
import com.chase.springcloud.service.blog.mapper.TagMapper;
import com.chase.springcloud.service.blog.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author zebin
 * @since 2022-11-04
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private RedisCache redisCache;
    @Autowired
    private TagMapper tagMapper;
    @Override
    public String getTagStr(String tagId) {
        return tagMapper.getTagStr(tagId);
    }
    @Override
    public List<TagRespDto> nestedList() {
        //缓存
        List<TagRespDto> tagRespDtos = (List<TagRespDto>) redisCache.getCacheObject("tags:");
        if (tagRespDtos == null || tagRespDtos.size() <= 0){
            tagRespDtos = baseMapper.selectNestedListByParentId("0");
            redisCache.setCacheObject("tags:", tagRespDtos);
        }
        return tagRespDtos;
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchImport(InputStream inputStream) {
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(inputStream, ExcelTagData.class, new ExcelTagDataListener(baseMapper))
                .excelType(ExcelTypeEnum.XLS).sheet().doRead();
        //删除tags缓存
        redisCache.deleteObject("tags:");
    }

    @Override
    public boolean addOrEdit(Tag tag) {
        if (StringUtils.isEmpty(tag.getId())) return baseMapper.insert(tag) > 0;
        else return baseMapper.updateById(tag) > 0;
    }

    @Override
    public boolean delete(Long id) {
        return baseMapper.deleteById(id) > 0;
    }
}
