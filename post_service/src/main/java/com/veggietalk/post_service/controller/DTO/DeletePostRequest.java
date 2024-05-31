package com.veggietalk.post_service.controller.DTO;

import lombok.*;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class DeletePostRequest {
    private UUID id;
    private UUID accountId;
    private String role;
}
