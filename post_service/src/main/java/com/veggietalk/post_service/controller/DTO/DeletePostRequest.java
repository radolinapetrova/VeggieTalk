package com.veggietalk.post_service.controller.DTO;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class DeletePostRequest {
    private Long id;
    private Long userId;
}
