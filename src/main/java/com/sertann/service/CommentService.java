package com.sertann.service;


import com.sertann.models.Comment;

public interface CommentService {

    Comment createComment(Comment comment, Integer postId, Integer userId) throws Exception;

    Comment findCommentById(Integer commentId) throws Exception;
    Comment likeComment(Integer commentId,Integer userId) throws Exception;



}
