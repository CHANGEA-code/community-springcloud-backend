package com.chase.springcloud.service.blog.service;

import com.chase.springcloud.service.base.model.User;
import com.chase.springcloud.service.blog.entity.Follow;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户关注 服务类
 * </p>
 *
 * @author zebin
 * @since 2022-11-04
 */
public interface FollowService extends IService<Follow> {

    List<User> getFollowedList(String userId);

    List<User> getFollowerList(String userId);

    /**
     * 统计关注数
     * @param userId
     * @return
     */
    Integer getFollowerNum(String userId);

    /**
     * 统计粉丝数
     * @param userId
     * @return
     */
    Integer getFollowedNum(String userId);
}
