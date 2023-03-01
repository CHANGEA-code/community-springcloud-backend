package com.chase.springcloud.service.blog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chase.springcloud.common.base.model.R;
import com.chase.springcloud.service.blog.dto.req.PostReqDto;
import com.chase.springcloud.service.blog.dto.resp.PageRespDto;
import com.chase.springcloud.service.blog.entity.Post;
import com.chase.springcloud.service.blog.dto.resp.PostRespDto;
import com.chase.springcloud.service.blog.feign.OssFileService;
import com.chase.springcloud.service.blog.service.PostService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * <p>
 * 话题表 前端控制器
 * </p>
 *
 * @author zebin
 * @since 2022-11-04
 */
@CrossOrigin
@RestController
@RequestMapping("/blog/post")
@Slf4j
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private OssFileService ossFileService;

    @GetMapping("page")
    public R page(PostReqDto postReqDto){
        PageRespDto<PostRespDto> pageList = postService.getPageList(postReqDto);
        return R.ok().data("data", pageList);
    }
    @GetMapping("page-by-userid/{id}")
    public R pageByUserId(@PathVariable("id")Long id,
                          PostReqDto postReqDto){
        PageRespDto<PostRespDto> page = postService.pageByUserId(id,postReqDto);
        return R.ok().data("data", page);
    }
    @GetMapping("get/{id}")
    public Callable<R> getDetail(@PathVariable("id")Long id) {
        //通过异步处理，开辟子线程来处理请求，防止web线程在此阻塞，提高系统吞吐量
        return new Callable<R>() {
            @Override
            public R call() throws Exception {
                PostRespDto post = postService.getDetail(id);
                if (post == null) return R.error().message("数据不存在");
                else return R.ok().data("data", post);
            }
        };
    }
    @GetMapping("search")
    public R search(PostReqDto postReqDto){
        PageRespDto<PostRespDto> pageList = postService.getPageList(postReqDto);
        return R.ok().data("data", pageList);
    }
    @GetMapping("recommend")
    public R recommend(PostReqDto postReqDto){
        List<PostRespDto> dtos = postService.recommendPost(postReqDto.getId(), postReqDto.getTagId(), postReqDto.getPageSize());
        return R.ok().data("data",dtos);
    }
    @PostMapping("save")
    public R add(@RequestBody PostReqDto postReqDto){
        String id = postService.addOrEditPost(postReqDto);
        if (id != null) return R.ok().message("添加成功").data("id", id);
        else return R.error().message("添加失败");
    }
    @DeleteMapping("remove/{id}")
    public R delete(@PathVariable("id")String id){
        //删除文章
        boolean b = postService.removePost(id);
        if (b) return R.ok().message("删除成功");
        else return R.error().message("删除失败");
    }
    @ApiOperation("根据id列表删除文章")
    @DeleteMapping("batch-remove")
    public R deleteBatch(
            @ApiParam(value = "id列表", required = true)
            @RequestBody List<String> idList){
        boolean result = postService.removePostByIds(idList);
        if(result){
            return R.ok().message("删除成功");
        }else{
            return R.error().message("数据不存在");
        }
    }
    @PutMapping("update")
    public R edit(@RequestBody PostReqDto postReqDto){
        String id = postService.addOrEditPost(postReqDto);
        if (id != null) return R.ok().message("更新成功").data("id",id);
        else return R.error().message("更新失败");
    }
}

