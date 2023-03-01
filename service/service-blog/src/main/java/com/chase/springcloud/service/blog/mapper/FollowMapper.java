package com.chase.springcloud.service.blog.mapper;

import com.chase.springcloud.service.base.model.User;
import com.chase.springcloud.service.blog.entity.Follow;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 用户关注 Mapper 接口
 * </p>
 *
 * @author zebin
 * @since 2022-11-04
 */
@Repository
public interface FollowMapper extends BaseMapper<Follow> {

    List<User> getFollowedList(String userId);

    List<User> getFollowerList(String userId);
}
