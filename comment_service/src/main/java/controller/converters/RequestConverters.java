package controller.converters;

import controller.DTO.CommentRequest;
import controller.DTO.CommentResponse;
import model.Comment;

public class RequestConverters {

    public static Comment CommentRequestConverter(CommentRequest request){
        return Comment.builder()
                .rating(request.getRating())
                .text(request.getText())
                .userId(request.getUserId())
                .postId(request.getPostId())
                .build();
    }

    public static CommentResponse CommentConverter(Comment comment){
        return CommentResponse.builder()
                .id(comment.getId())
                .userId(comment.getUserId())
                .postId(comment.getPostId())
                .text(comment.getText())
                .rating(comment.getRating())
                .build();
    }
}
