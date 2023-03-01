package com.chase.springcloud.service.blog.service;

import com.chase.springcloud.service.blog.entity.Tip;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 每日赠言 服务类
 * </p>
 *
 * @author zebin
 * @since 2022-11-04
 */
public interface TipService extends IService<Tip> {

    Tip getTodayTip();
}
