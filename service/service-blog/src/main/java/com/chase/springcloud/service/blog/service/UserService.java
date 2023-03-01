package com.chase.springcloud.service.blog.service;

import com.chase.springcloud.common.base.model.R;
import com.chase.springcloud.service.base.model.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author zebin
 * @since 2022-11-04
 */
public interface UserService extends IService<User> {
    Boolean register(User user);
    String login(User user);
    User getInfo();
    Boolean logout();
    Boolean updateUser(User user);
    Boolean updatePass(String userId, String oldPass, String newPass);
}
