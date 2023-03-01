package com.chase.springcloud.service.blog;

import com.chase.springcloud.common.base.util.RedisCache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class RedisTest {
    @Autowired
    RedisCache redisCache;
    @Test
    public void test(){
//        Collection<String> keys = redisCache.keys("");
        Map<String, Object> cacheMap = redisCache.getCacheMap("post:latest");
        ArrayList<String> list = new ArrayList<>();
        list.add("post:hot");
        list.add("post:latest");
        long b = redisCache.deleteObject(list);
        System.out.println(cacheMap.entrySet().toString());
        System.out.println(b);
    }
}
