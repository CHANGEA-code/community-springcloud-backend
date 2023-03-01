package com.chase.springcloud.service.blog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chase.springcloud.common.base.model.R;
import com.chase.springcloud.service.blog.dto.resp.CommentRespDto;
import com.chase.springcloud.service.blog.entity.Comment;
import com.chase.springcloud.service.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog/comment")
@CrossOrigin
public class CommentController {
    @Autowired
    private CommentService commentService;
    @GetMapping("get_first_layer_comment")
    public R page(@RequestParam(value = "topicId", defaultValue = "1") String topicId){
        List<CommentRespDto> comments = commentService.getFirstLayerComments(topicId);
        return R.ok().data("comments", comments);
    }
    @PostMapping("add")
    public R add(@RequestBody Comment comment){
        if (commentService.addComment(comment)) return R.ok().message("添加成功");
        else return R.error().message("添加失败");
    }
    @DeleteMapping("{commentId}")
    public R delete(@PathVariable("commentId") String commentId){
         if (commentService.deleteCommentAndChilds(commentId))
             return R.ok().message("删除成功");
         else
             return R.error().message("删除失败");
    }
    @PostMapping("update")
    public R update(@RequestBody Comment comment){
        if (commentService.updateComment(comment)) return R.ok().message("修改成功");
        else return R.error().message("修改失败");
    }
}
