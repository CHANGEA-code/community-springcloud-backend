package com.chase.springcloud.service.blog.dto.resp;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.chase.springcloud.service.base.model.BaseEntity;
import com.chase.springcloud.service.base.model.User;
import com.chase.springcloud.service.blog.dto.req.PageReqDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostRespDto implements Serializable{
    private static final long serialVersionUID=1L;
    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "markdown内容")
    private String content;
    @ApiModelProperty(value = "作者")
    private User author;
    @ApiModelProperty(value = "评论统计")
    private Integer comments;
    @ApiModelProperty(value = "浏览统计")
    private Integer view;
    @ApiModelProperty(value = "文章封面")
    private String cover;
    @ApiModelProperty("一级标签id")
    private String parentTagId;
    @ApiModelProperty(value = "二级标签id")
    private String tagId;
    @ApiModelProperty(value = "标签分类中文")
    private String tagName;
    @ApiModelProperty(value = "点赞数")
    private Integer zans;
    @ApiModelProperty("发布时间")
    private Date createTime;
    @ApiModelProperty("修改时间")
    private Date updateTime;
}
