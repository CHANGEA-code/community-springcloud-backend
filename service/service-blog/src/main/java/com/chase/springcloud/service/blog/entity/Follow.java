package com.chase.springcloud.service.blog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.chase.springcloud.service.base.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户关注
 * </p>
 *
 * @author zebin
 * @since 2022-11-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("blog_follow")
@ApiModel(value="Follow对象", description="用户关注")
@AllArgsConstructor
public class Follow extends BaseEntity {
    private static final long serialVersionUID=1L;
    @ApiModelProperty(value = "关注人ID")
    private String followerId;
    @ApiModelProperty(value = "被关注人ID")
    private String followedId;
}
