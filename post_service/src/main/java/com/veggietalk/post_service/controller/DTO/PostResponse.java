package com.veggietalk.post_service.controller.DTO;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class PostResponse {
    private Long id;
    private Long userId;
    private String description;
    private String date;
}
