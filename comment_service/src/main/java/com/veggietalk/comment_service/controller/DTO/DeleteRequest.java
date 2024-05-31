package com.veggietalk.comment_service.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class DeleteRequest {
    private UUID commentId;
    private String role;
    private UUID accountId;
}
