package com.chase.springcloud.service.blog.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.chase.springcloud.service.base.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 评论表
 * </p>
 *
 * @author zebin
 * @since 2022-11-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("blog_comment")
@ApiModel(value="Comment对象", description="评论表")
public class Comment extends BaseEntity {
    @ApiModelProperty(value = "内容")
    private String content;
    @ApiModelProperty(value = "作者ID")
    private String userId;
    @ApiModelProperty(value = "文章id")
    private String topicId;
    @ApiModelProperty(value = "回复id，回复哪条评论；0表示第一层回复")
    private String replyId;
    @ApiModelProperty(value = "回复用户名")
    private String replyName;
}
