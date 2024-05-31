package com.veggietalk.comment_service.controller.converters;

import com.veggietalk.comment_service.controller.DTO.CommentRequest;
import com.veggietalk.comment_service.controller.DTO.CommentResponse;
import com.veggietalk.comment_service.model.Comment;

public class RequestConverters {

    public static Comment CommentRequestConverter(CommentRequest request){
        return Comment.builder()
                .rating(request.getRating())
                .text(request.getText())
                .accountId(request.getAccountId())
                .postId(request.getPostId())
                .build();
    }

    public static CommentResponse CommentConverter(Comment comment){
        return CommentResponse.builder()
                .id(comment.getId())
                .accountId(comment.getAccountId())
                .postId(comment.getPostId())
                .text(comment.getText())
                .rating(comment.getRating())
                .build();
    }
}
