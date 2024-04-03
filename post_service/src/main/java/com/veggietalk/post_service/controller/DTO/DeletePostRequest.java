package com.veggietalk.post_service.controller.DTO;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class DeletePostRequest {
    private Long id;
    private Long userId;
}
