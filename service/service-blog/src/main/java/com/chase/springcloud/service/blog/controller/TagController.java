package com.chase.springcloud.service.blog.controller;


import com.chase.springcloud.common.base.model.R;
import com.chase.springcloud.common.base.model.ResultCodeEnum;
import com.chase.springcloud.common.base.util.ExceptionUtils;
import com.chase.springcloud.service.base.exception.GuliException;
import com.chase.springcloud.service.blog.dto.resp.TagRespDto;
import com.chase.springcloud.service.blog.entity.Tag;
import com.chase.springcloud.service.blog.service.TagService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 标签表 前端控制器
 * </p>
 *
 * @author zebin
 * @since 2022-11-04
 */
@RestController
@RequestMapping("/blog/tag")
@CrossOrigin
@Slf4j
public class TagController {
    @Autowired
    private TagService tagService;
    @ApiOperation(value = "通过二级标签id获取tag字符串")
    @GetMapping("get-str/{id}")
    public R getStr(@PathVariable("id")Long id){
        String tag = tagService.getTagStr(id+"");
        return R.ok().data("data", tag);
    }
    @ApiOperation(value = "获取嵌套数据列表")
    @GetMapping("nested-list")
    public R all(){
        List<TagRespDto> tagRespDtoList = tagService.nestedList();
        return R.ok().data("items", tagRespDtoList);
    }
    @ApiOperation(value = "通过Excel批量导入标签")
    @PostMapping("import")
    public R addBatch(
        @ApiParam(value = "Excel文件", required = true)
        @RequestParam("file") MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            tagService.batchImport(inputStream);
            return R.ok().message("批量导入成功");
        } catch (Exception e) {
            log.error(ExceptionUtils.getMessage(e));
            throw new GuliException(ResultCodeEnum.EXCEL_DATA_IMPORT_ERROR);
        }
    }
    @ApiOperation(value = "添加标签")
    @PostMapping("add")
    public R addTag(Tag tag) {
        if (tagService.addOrEdit(tag)) return R.ok().message("添加成功");
        else return R.error().message("添加失败");
    }
    @ApiOperation(value = "删除标签")
    @DeleteMapping("delete/{id}")
    public R delete(@PathVariable("id")Long id) {
        if (tagService.delete(id)) return R.ok().message("删除成功");
        else return R.error().message("删除失败");
    }
    @ApiOperation(value = "修改标签")
    @PostMapping("edit")
    public R edit(Tag tag) {
        if (tagService.addOrEdit(tag)) return R.ok().message("修改成功");
        else return R.error().message("修改失败");
    }
}

