package com.chase.springcloud.service.blog;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chase.springcloud.service.blog.entity.Post;
import com.chase.springcloud.service.blog.dto.resp.PostRespDto;
import com.chase.springcloud.service.blog.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MPTest {
    @Autowired
    private PostService postService;
    @Test
    public void test01() {
        int pageNum = 0;
        int pageSize = 5;
        Page<Post> postPage = new Page<>(pageNum, pageSize);
        QueryWrapper<Post> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("create_time");
        Page<Post> page = postService.page(postPage, wrapper);
    }
    @Test
    public void test02(){
//        PostRespDto postVO = new PostRespDto();
//        postVO.setId("25q2871249712");
//        postVO.setTitle("测试");
//        postVO.setContent("内容");
//
//        Post post = new Post();
//        BeanUtils.copyProperties(postVO, post);
//        System.out.println(post.getId());
//        System.out.println(post.toString());
    }
}
