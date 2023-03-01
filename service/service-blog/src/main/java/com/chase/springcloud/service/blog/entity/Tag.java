package com.chase.springcloud.service.blog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.chase.springcloud.service.base.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 标签表
 * </p>
 *
 * @author zebin
 * @since 2022-11-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("blog_tag")
@ApiModel(value="Tag对象", description="标签分类表")
public class Tag extends BaseEntity {
    @ApiModelProperty(value = "标签名称")
    private String name;
    @ApiModelProperty(value = "父标签id")
    private String parentId;
}
