package com.veggietalk.post_service.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class CreatePostRequest {
    private Long user_id;
    private String description;
}
