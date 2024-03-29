package com.veggietalk.post_service.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DeletePostRequest {
    private Long id;
    private Long userId;
}
