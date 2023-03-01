package com.chase.springcloud.service.blog;

import com.chase.springcloud.service.blog.entity.Post;
import com.chase.springcloud.service.blog.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class PostTest {
    @Autowired
    private PostService postService;
    @Test
    public void test01(){
        ArrayList<String> ids = new ArrayList<>();
        ids.add("1616237413460467714");
        ids.add("1616722197672472578");
//        List<Post> posts = postService.getPostByIdBatch(ids);
//        System.out.println(posts.toString());
    }
}
