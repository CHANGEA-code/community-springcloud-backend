package com.chase.springcloud.service.blog.dto.resp;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chase.springcloud.service.base.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentRespDto implements Serializable {
    private static final long serialVersionUID=1L;
    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty(value = "内容")
    private String content;
    @ApiModelProperty(value = "用户id")
    private String userId;
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "email哈希")
    private String emailHash;
    @ApiModelProperty(value = "文章id")
    private String topicId;
    @ApiModelProperty(value = "回复id，回复哪条评论；0表示第一层回复")
    private String replyId;
    @ApiModelProperty(value = "回复用户名")
    private String replyName;
    @ApiModelProperty(value = "子评论列表")
    private List<CommentRespDto> childs;
    @ApiModelProperty("发布时间")
    private Date createTime;
    @ApiModelProperty("修改时间")
    private Date updateTime;
}
