package com.chase.springcloud.service.base.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户表(User)实体类
 *
 * @author 三更
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "blog_user")
public class User extends BaseEntity {
    private static final long serialVersionUID = -40356785423868312L;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 账号状态（0正常 1停用）
     */
    private String status;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String phonenumber;
    /**
     * 用户性别（0男，1女，2未知）
     */
    private String sex;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 用户类型（0管理员，1普通用户）
     */
    private String userType;
    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    private Integer delFlag;

    /**
     * email哈希，用于显示头像
     */
    private String emailHash;
    /**
     * 简介
     */
    private String bio;
    /**
     * 粉丝数
     */
    private Integer fansNum;
    /**
     * 关注数
     */
    private Integer focusNum;
    /**
     * 文章数
     */
    private Integer postNum;
}
