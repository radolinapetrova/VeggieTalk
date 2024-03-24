package com.veggietalk.post_service.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreatePostRequest {
    private String date;
    private Long user_id;
    private String description;
}
