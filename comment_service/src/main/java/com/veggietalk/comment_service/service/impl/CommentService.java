package com.veggietalk.comment_service.service.impl;

import com.veggietalk.comment_service.model.Comment;
import com.veggietalk.comment_service.model.Rating;

import java.util.List;
import java.util.UUID;

public interface CommentService {
    List<Comment> getPostComments(UUID postId) throws IllegalArgumentException;

    List<Comment> getPostCommentsByRating(UUID postId, Rating rating, UUID userId) throws IllegalAccessException, IllegalArgumentException;

    List<Comment> getCommentsByRating(Rating rating, UUID userId) throws IllegalAccessException, IllegalArgumentException;

    void deleteComment(UUID commentId, UUID userId, String role) throws IllegalAccessException;

    Comment createComment(Comment comment) throws IllegalAccessException;

}
