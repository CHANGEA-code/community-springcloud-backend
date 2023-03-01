package com.chase.springcloud.service.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chase.springcloud.common.base.model.R;
import com.chase.springcloud.common.base.util.JwtUtil;
import com.chase.springcloud.common.base.util.RedisCache;
import com.chase.springcloud.service.base.model.LoginUser;
import com.chase.springcloud.service.base.model.User;
import com.chase.springcloud.service.blog.mapper.UserMapper;
import com.chase.springcloud.service.blog.service.FollowService;
import com.chase.springcloud.service.blog.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;
/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author zebin
 * @since 2022-11-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private FollowService followService;

    @Override
    public Boolean register(User user) {
        //判断是否有该用户
        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("user_name", user.getUserName());
        User selectOne = baseMapper.selectOne(query);
        if (selectOne != null) return false;
        //密码加密
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        return baseMapper.insert(user) > 0;
    }

    @Override
    public String login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //使用userid生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //authenticate存入redis
        redisCache.setCacheObject("login:"+userId,loginUser);
        //把token响应给前端
        return jwt;
    }

    @Override
    public User getInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        return loginUser.getUser();
    }

    @Override
    public Boolean logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userid = loginUser.getUser().getId();
        return redisCache.deleteObject("login:" + userid);
    }

    @Override
    public Boolean updateUser(User user) {
        int i = baseMapper.updateById(user);
        if (i > 0){
            //更新redis中的信息
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            LoginUser loginUser = (LoginUser) authentication.getPrincipal();
            loginUser.setUser(user);
            redisCache.setCacheObject("login:"+user.getId(),loginUser);
            return true;
        }
        return false;
    }

    @Override
    public Boolean updatePass(String userId, String oldPass, String newPass) {
        User user = baseMapper.selectById(userId);
        if (user == null) return false;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(oldPass, user.getPassword())) return false;
        user.setPassword(encoder.encode(newPass));
        int i = baseMapper.updateById(user);
        if (i <= 0) return false;
        return true;
    }
}
