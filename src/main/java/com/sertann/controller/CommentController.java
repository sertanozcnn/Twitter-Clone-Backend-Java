package com.sertann.controller;


import com.sertann.models.Comment;
import com.sertann.models.Post;
import com.sertann.models.User;
import com.sertann.service.CommentService;
import com.sertann.service.PostService;
import com.sertann.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @PostMapping("/api/comments/post/{postId}") //Create Comment
    public Comment createComment(
            @RequestBody Comment comment,
            @RequestHeader("Authorization") String jwt,
            @PathVariable("postId") Integer postId) throws Exception {

        User user = userService.findUserByJwt(jwt);

        return commentService.createComment(comment, postId, user.getId());
    }

    @PutMapping("/api/comments/like/{commentId}")
    public Comment likeComment(
            @RequestHeader("Authorization") String jwt,
            @PathVariable("commentId") Integer commentId) throws Exception {


        User user = userService.findUserByJwt(jwt);

        return commentService.likeComment(commentId, user.getId());
    }





}
