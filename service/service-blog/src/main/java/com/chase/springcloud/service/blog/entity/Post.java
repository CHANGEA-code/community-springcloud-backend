package com.chase.springcloud.service.blog.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.chase.springcloud.service.base.model.BaseEntity;
import com.chase.springcloud.service.base.model.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 话题表
 * </p>
 *
 * @author zebin
 * @since 2022-11-04
 */
@Data
@Builder
@TableName("blog_post")
@ApiModel(value="Post对象", description="话题表")
public class Post implements Serializable {
    private static final long serialVersionUID=1L;
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
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
    @ApiModelProperty(value = "点赞数")
    private Integer zans;
    @TableLogic
    @ApiModelProperty(value = "逻辑删除")
    private Integer deleted;
    @ApiModelProperty(value = "文章封面")
    private String cover;
    @ApiModelProperty(value = "标签的二级分类id")
    private String tagId;
    @ApiModelProperty(value = "发布时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
