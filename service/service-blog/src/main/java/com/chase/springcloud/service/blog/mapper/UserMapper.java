package com.chase.springcloud.service.blog.mapper;

import com.chase.springcloud.service.base.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author zebin
 * @since 2022-11-04
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

}
