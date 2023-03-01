package com.chase.springcloud.service.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chase.springcloud.common.base.util.RedisCache;
import com.chase.springcloud.service.blog.entity.Tip;
import com.chase.springcloud.service.blog.mapper.TipMapper;
import com.chase.springcloud.service.blog.service.TipService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 每日赠言 服务实现类
 * </p>
 *
 * @author zebin
 * @since 2022-11-04
 */
@Service
public class TipServiceImpl extends ServiceImpl<TipMapper, Tip> implements TipService {
    @Autowired
    private TipMapper tipMapper;
    @Autowired
    private RedisCache redisCache;
    @Override
    public Tip getTodayTip() {
        Tip tip = (Tip)redisCache.getCacheObject("today:tip");
        if (tip == null){
            tip = tipMapper.randGetTip();
            //计算今日剩余时间
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_YEAR, 1);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.MILLISECOND, 0);
            long timeout = (cal.getTimeInMillis() - System.currentTimeMillis()) / 1000;
            redisCache.setCacheObject("today:tip", tip, timeout, TimeUnit.SECONDS);
        }
        return tip;
    }
}
