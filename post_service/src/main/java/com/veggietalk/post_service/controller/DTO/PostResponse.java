package com.veggietalk.post_service.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PostResponse {
    private Long id;
    private Long userId;
    private String description;
    private String date;
}
