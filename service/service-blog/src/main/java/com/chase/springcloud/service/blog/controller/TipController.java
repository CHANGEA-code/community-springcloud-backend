package com.chase.springcloud.service.blog.controller;


import com.chase.springcloud.common.base.model.R;
import com.chase.springcloud.service.blog.entity.Tip;
import com.chase.springcloud.service.blog.service.TipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 每日赠言 前端控制器
 * </p>
 *
 * @author zebin
 * @since 2022-11-04
 */
@CrossOrigin
@RestController
@RequestMapping("/blog/tip")
public class TipController {
    @Autowired
    private TipService tipService;
    @GetMapping("today")
    public R rand(){
        Tip tip = tipService.getTodayTip();
        if (tip != null){
            return R.ok().data("tip", tip);
        }else {
            return R.error().message("获取失败");
        }
    }
}

