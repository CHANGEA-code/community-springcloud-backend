package com.chase.springcloud.service.blog.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chase.springcloud.service.blog.entity.ExcelTagData;
import com.chase.springcloud.service.blog.entity.Tag;
import com.chase.springcloud.service.blog.mapper.TagMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor //全参
@NoArgsConstructor //无参
public class ExcelTagDataListener extends AnalysisEventListener<ExcelTagData> {
    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */
    private TagMapper tagMapper;
    /**
     * 这个每一条数据解析都会来调用
     */
    @Override
    public void invoke(ExcelTagData data, AnalysisContext context) {
        log.info("解析到一条数据:{}", data);
        //处理读取进来的数据
        String titleLevelOne = data.getLevelOneTitle();
        String titleLevelTwo = data.getLevelTwoTitle();
        //判断一级分类是否重复
        Tag tagLevelOne = this.getByTitle(titleLevelOne);
        String parentId = null;
        if(tagLevelOne == null) {
            //将一级分类存入数据库
            Tag tag = new Tag();
            tag.setParentId("0");
            tag.setName(titleLevelOne);//一级分类名称
            tagMapper.insert(tag);
            parentId = tag.getId();
        }else{
            parentId = tagLevelOne.getId();
        }
        //判断二级分类是否重复
        Tag subjectLevelTwo = this.getSubByTitle(titleLevelTwo, parentId);
        if(subjectLevelTwo == null){
            //将二级分类存入数据库
            Tag tag = new Tag();
            tag.setName(titleLevelTwo);
            tag.setParentId(parentId);
            tagMapper.insert(tag);//添加
        }
    }
    /**
     * 所有数据解析完成了 都会来调用
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("所有数据解析完成！");
    }

    /**
     * 根据分类名称查询这个一级分类是否存在
     * @param title
     * @return
     */
    private Tag getByTitle(String title) {
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", title);
        queryWrapper.eq("parent_id", "0");//一级分类
        return tagMapper.selectOne(queryWrapper);
    }
    /**
     * 根据分类名称和父id查询这个二级分类是否存在
     * @param title
     * @return
     */
    private Tag getSubByTitle(String title, String parentId) {
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", title);
        queryWrapper.eq("parent_id", parentId);
        return tagMapper.selectOne(queryWrapper);
    }
}
