package com.chase.springcloud.service.blog.entity;


import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ExcelTagData {
    @ExcelProperty(value = "一级分类")
    private String levelOneTitle;
    @ExcelProperty(value = "二级分类")
    private String levelTwoTitle;
}
