package com.veggietalk.post_service.controller.DTO;

import lombok.*;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class DeletePostRequest {
    private UUID id;
    private UUID userId;
    private String role;
}
