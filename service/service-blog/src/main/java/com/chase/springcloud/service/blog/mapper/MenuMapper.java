package com.chase.springcloud.service.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chase.springcloud.service.base.model.Menu;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {
    List<String> selectPermsByUserId(String id);
}
