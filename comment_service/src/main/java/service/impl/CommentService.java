package service.impl;

import model.Comment;
import model.Rating;

import java.util.List;

public interface CommentService {
    List<Comment> getPostComments(Long postId) throws IllegalArgumentException;

    List<Comment> getPostCommentsByRating(Long postId, Rating rating, Long userId) throws IllegalAccessException, IllegalArgumentException;

    List<Comment> getCommentsByRating(Rating rating, Long userId) throws IllegalAccessException, IllegalArgumentException;

    void deleteComment(Long commentId, Long userId, String role) throws IllegalAccessException;

    Comment createComment(Comment comment) throws IllegalAccessException;

}
