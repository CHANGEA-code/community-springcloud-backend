package com.chase.springcloud.service.blog.mapper;

import com.chase.springcloud.service.blog.entity.Tip;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 每日赠言 Mapper 接口
 * </p>
 *
 * @author zebin
 * @since 2022-11-04
 */
@Repository
public interface TipMapper extends BaseMapper<Tip> {

    Tip randGetTip();
}
