package com.chase.springcloud.service.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chase.springcloud.service.base.model.User;
import com.chase.springcloud.service.blog.entity.Follow;
import com.chase.springcloud.service.blog.mapper.FollowMapper;
import com.chase.springcloud.service.blog.service.FollowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户关注 服务实现类
 * </p>
 *
 * @author zebin
 * @since 2022-11-04
 */
@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements FollowService {

    @Autowired
    private FollowMapper followMapper;

    /**
     * 查询我的关注
     * @param userId
     * @return
     */
    @Override
    public List<User> getFollowedList(String userId) {
        return followMapper.getFollowedList(userId);
    }

    /**
     * 查询我的粉丝
     * @param userId
     * @return
     */
    @Override
    public List<User> getFollowerList(String userId) {
        return followMapper.getFollowerList(userId);
    }

    @Override
    public Integer getFollowerNum(String userId) {
        QueryWrapper<Follow> wrapper = new QueryWrapper<>();
        wrapper.eq("follower_id", userId);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }

    @Override
    public Integer getFollowedNum(String userId) {
        QueryWrapper<Follow> wrapper = new QueryWrapper<>();
        wrapper.eq("followed_id", userId);
        Integer count = baseMapper.selectCount(wrapper);
        return count;
    }
}
