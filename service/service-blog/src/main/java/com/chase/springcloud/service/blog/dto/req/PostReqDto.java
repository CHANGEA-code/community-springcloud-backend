package com.chase.springcloud.service.blog.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostReqDto extends PageReqDto implements Serializable {
    private static final long serialVersionUID=1L;
    @ApiModelProperty(value = "主键")
    private String id;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "markdown内容")
    private String content;
    @ApiModelProperty(value = "作者ID")
    private String authorId;
    @ApiModelProperty(value = "评论统计")
    private Integer comments;
    @ApiModelProperty(value = "浏览统计")
    private Integer view;
    @ApiModelProperty(value = "作者姓名")
    private String author;
    @ApiModelProperty(value = "文章封面")
    private String cover;
    @ApiModelProperty(value = "点赞数")
    private Integer zans;
    @ApiModelProperty(value = "标签：最新，最热")
    private String tab;
    @ApiModelProperty(value = "查询时间，起始")
    private String gmtCreateBegin;
    @ApiModelProperty(value = "查询时间，结尾")
    private String gmtCreateEnd;
    @ApiModelProperty(value="推荐文章的标签id")
    private String tagId;
}
