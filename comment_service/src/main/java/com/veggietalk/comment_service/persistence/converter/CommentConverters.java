package com.veggietalk.comment_service.persistence.converter;

import com.veggietalk.comment_service.model.Comment;
import com.veggietalk.comment_service.persistence.model.CommentEntity;

public class CommentConverters {

    public static CommentEntity CommentConverter (Comment comment){
        CommentEntity entity = CommentEntity.builder()
                .text(comment.getText())
                .accountId(comment.getAccountId())
                .rating(comment.getRating())
                .postId(comment.getPostId())
                .build();

        if (comment.getId() != null){
            entity.setId(comment.getId());
        }

        return entity;
    }


    public static Comment CommentEntityConverter(CommentEntity entity){
        return Comment.builder()
                .id(entity.getId())
                .rating(entity.getRating())
                .text(entity.getText())
                .accountId(entity.getAccountId())
                .postId(entity.getPostId())
                .build();
    }

}
