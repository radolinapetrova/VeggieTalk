package com.veggietalk.comment_service.persistence.impl;

import com.veggietalk.comment_service.persistence.CommentRepo;
import com.veggietalk.comment_service.persistence.DBRepo.CommentDBRepo;
import lombok.RequiredArgsConstructor;
import com.veggietalk.comment_service.model.Comment;
import com.veggietalk.comment_service.model.Rating;
import org.springframework.stereotype.Repository;
import com.veggietalk.comment_service.persistence.converter.CommentConverters;
import com.veggietalk.comment_service.persistence.model.CommentEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
@RequiredArgsConstructor
public class CommentRepoImpl implements CommentRepo {

    private final CommentDBRepo commentDBRepo;

    @Override
    public List<Comment> getPostComments(UUID postId) throws IllegalArgumentException{
        List<CommentEntity> result = commentDBRepo.findAllByPostId(postId);

        if (!result.isEmpty()){
            return result.stream().map(CommentConverters::CommentEntityConverter).toList();
        }
        throw new IllegalArgumentException("There are comments for this post");
    }

    @Override
    public List<Comment> getPostCommentsByRating(UUID postId, Rating rating) throws IllegalArgumentException{

        List<CommentEntity> result = commentDBRepo.findAllByPostIdAndRating(postId, rating);

        if(!result.isEmpty()){
            return result.stream().map(CommentConverters::CommentEntityConverter).toList();
        }
          throw new IllegalArgumentException("There are no comments with this rating for this post;") ;
    }

    @Override
    public List<Comment> getCommentsByRating(Rating rating) throws IllegalArgumentException{

        List<CommentEntity> result = commentDBRepo.findAllByRating(rating);

        if(!result.isEmpty()){
            return result.stream().map(CommentConverters::CommentEntityConverter).toList();
        }
        throw new IllegalArgumentException("There are no comments with the selected rating");
    }

    @Override
    public void deleteComment(UUID commentId) {
        commentDBRepo.deleteById(commentId);
    }

    @Override
    public Comment createComment(Comment comment) {
        return CommentConverters.CommentEntityConverter(commentDBRepo
                .saveAndFlush(CommentConverters.CommentConverter(comment)));
    }

    @Override
    public Comment findById(UUID id) throws IllegalArgumentException{
        Optional<CommentEntity> entity = commentDBRepo.findById(id);

        if(entity.isPresent()){
            return CommentConverters.CommentEntityConverter(entity.get());
        }
        throw new IllegalArgumentException("No comment with the selected id exists");
    }
}
