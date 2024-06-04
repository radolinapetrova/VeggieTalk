package com.veggietalk.comment_service.persistence;

import com.veggietalk.comment_service.model.Comment;
import com.veggietalk.comment_service.model.Rating;

import java.util.IllegalFormatPrecisionException;
import java.util.List;
import java.util.UUID;

public interface CommentRepo {
    List<Comment> getPostComments(UUID postId) throws IllegalArgumentException;
    List<Comment> getPostCommentsByRating(UUID postId,Rating rating) throws IllegalArgumentException;
    List<Comment> getCommentsByRating(Rating rating) throws IllegalArgumentException;

    void deleteComment (UUID commentId);

    Comment createComment(Comment comment);

    Comment findById(UUID id) throws IllegalArgumentException;

    void deleteALlByAccountId(UUID accountId) throws IllegalArgumentException;

    void deleteAllByPostId(UUID postId) throws IllegalArgumentException;

}
