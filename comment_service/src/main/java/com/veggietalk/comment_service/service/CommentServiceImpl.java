package com.veggietalk.comment_service.service;

import lombok.AllArgsConstructor;
import com.veggietalk.comment_service.model.Comment;
import com.veggietalk.comment_service.model.Rating;
import org.springframework.stereotype.Service;
import com.veggietalk.comment_service.persistence.CommentRepo;
import com.veggietalk.comment_service.service.impl.CommentService;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepo commentRepo;

    @Override
    public List<Comment> getPostComments(UUID postId) throws IllegalArgumentException {
        return commentRepo.getPostComments(postId);
    }

    @Override
    public List<Comment> getPostCommentsByRating(UUID postId, Rating rating, UUID userId) throws IllegalAccessException, IllegalArgumentException {
        if (Objects.equals(userId, null)){
            throw new IllegalAccessException("Create an account if you want to filter the comments");
        }

        return commentRepo.getPostCommentsByRating(postId, rating);
    }

    @Override
    public List<Comment> getCommentsByRating(Rating rating, UUID userId) throws IllegalAccessException, IllegalArgumentException {
        if(Objects.equals(userId, null)){
            throw new IllegalAccessException("Create an account if you want to filter the comments");
        }

        return commentRepo.getCommentsByRating(rating);
    }

    @Override
    public void deleteComment(UUID commentId, UUID userId, String role) throws IllegalAccessException{
        Comment comment = commentRepo.findById(commentId);

        if(Objects.equals(comment.getAccountId(), userId) || Objects.equals(role, "ADMIN")){
            commentRepo.deleteComment(commentId);
        }
        else throw new IllegalAccessException("You are not authorized to delete this post");
    }

    @Override
    public Comment createComment(Comment comment) throws IllegalAccessException{
        if(Objects.equals(comment.getAccountId(), null)){
            throw new IllegalAccessException("You are not authorized to create a post");
        }
        return commentRepo.createComment(comment);
    }
}
