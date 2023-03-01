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
 * 每日赠言
 * </p>
 *
 * @author zebin
 * @since 2022-11-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("blog_tip")
@ApiModel(value="Tip对象", description="每日赠言")
public class Tip extends BaseEntity {
    @ApiModelProperty(value = "内容")
    private String content;
    @ApiModelProperty(value = "作者")
    private String author;
    @ApiModelProperty(value = "1：使用，0：过期")
    private Integer type;
}
