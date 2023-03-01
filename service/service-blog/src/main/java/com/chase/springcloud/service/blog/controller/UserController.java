package com.chase.springcloud.service.blog.controller;

import com.chase.springcloud.common.base.model.R;
import com.chase.springcloud.service.base.model.User;
import com.chase.springcloud.service.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("register")
    public R register(@RequestBody User user){
        if (userService.register(user)) return R.ok().message("注册成功");
        else return R.error().message("注册失败");
    }
    @PostMapping("login")
    public R login(@RequestBody User user) {
        String jwt = userService.login(user);
        return R.ok().data("token",jwt).message("登陆成功");
    }
    @GetMapping("info")
    public R info() {
        User user = userService.getInfo();
        return R.ok().data("user", user).message("获取成功");
    }
    @GetMapping("get/{id}")
    public R getById(@PathVariable("id")Long id){
        User user = userService.getById(id);
        return R.ok().data("user", user).message("获取成功");
    }
    @PostMapping("logout")
    public R logout(){
        if (userService.logout()) return R.ok().message("退出成功");
        else return R.error().message("退出异常");
    }
    @PutMapping("update")
    public R update(@RequestBody User user){
        if (userService.updateUser(user))
            return R.ok().message("更新信息成功");
        else
            return R.error().message("更新信息失败");
    }
    @PutMapping("update-pass/{userId}")
    public R updatePass(@PathVariable("userId")String userId,
                    @RequestBody Map<String, String> map){
        String oldPass = map.get("oldPass");
        String newPass = map.get("newPass");
        if (userService.updatePass(userId, oldPass, newPass))
            return R.ok().message("密码修改成功");
        else
            return R.error().message("密码修改失败");
    }
}
