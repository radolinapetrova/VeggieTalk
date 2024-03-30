package com.veggietalk.post_service.controller.DTO;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class PostRequest {
    private Long userId;
    private String description;
}
