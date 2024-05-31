package com.veggietalk.comment_service.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import com.veggietalk.comment_service.model.Rating;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class FilterCommentsRequest {
    private UUID accountId;
    private UUID postId;
    private Rating rating;
}
