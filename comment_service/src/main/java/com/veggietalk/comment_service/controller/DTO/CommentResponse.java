package com.veggietalk.comment_service.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import com.veggietalk.comment_service.model.Rating;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class CommentResponse {
    private UUID id;
    private String text;
    private UUID accountId;
    private UUID postId;
    private Rating rating;
}
