package com.chase.springcloud.service.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chase.springcloud.common.base.model.R;
import com.chase.springcloud.service.base.model.User;
import com.chase.springcloud.service.blog.entity.Follow;
import com.chase.springcloud.service.blog.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户关注 前端控制器
 * </p>
 *
 * @author zebin
 * @since 2022-11-04
 */
@CrossOrigin
@RestController
@RequestMapping("/blog/follow")
public class FollowController {
    @Autowired
    private FollowService followService;

    /**
     * 查询我的关注
     * @param userId
     * @return
     */
    @GetMapping("get-followed/{userId}")
    public R listFocus(@PathVariable("userId") String userId){
        List<User> followeds= followService.getFollowedList(userId);
        return R.ok().data("data", followeds);
    }

    /**
     * 查询我的粉丝
     * @param userId
     * @return
     */
    @GetMapping("get-follower/{userId}")
    public R listFans(@PathVariable("userId") String userId){
        List<User> followers= followService.getFollowerList(userId);
        return R.ok().data("data", followers);
    }

    @GetMapping("isfollow/{userId}/{authorId}")
    public R isFollow(@PathVariable("userId") String userId, @PathVariable("authorId") String authorId){
        QueryWrapper<Follow> query = new QueryWrapper<>();
        query.eq("follower_id", userId);
        query.eq("followed_id", authorId);
        Follow one = followService.getOne(query);
        if (one != null){
            return R.ok().data("isFollow", true);
        }else {
            return R.ok().data("isFollow", false);
        }
    }

    /**
     * 点击关注
     * @param userId
     * @param followedId
     * @return
     */
    @PostMapping("add/{userId}/{followedId}")
    public R add(@PathVariable("userId") String userId, @PathVariable("followedId") String followedId){
        Follow follow = new Follow(userId, followedId);
        boolean save = followService.save(follow);
        if (save){
            return R.ok().message("关注成功");
        }else {
            return R.error().message("关注失败");
        }
    }
    /**
     * 取消关注
     * @param userId
     * @param followedId
     * @return
     */
    @DeleteMapping("delete/{userId}/{followedId}")
    public R delete(@PathVariable("userId") String userId, @PathVariable("followedId") String followedId){
        QueryWrapper<Follow> query = new QueryWrapper<>();
        query.eq("follower_id", userId);
        query.eq("followed_id", followedId);
        boolean save = followService.remove(query);
        if (save){
            return R.ok().message("取消关注成功");
        }else {
            return R.error().message("取消关注失败");
        }
    }

}

