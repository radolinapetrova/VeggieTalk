package com.veggietalk.account_service.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
public class DeleteAccountRequest {
    private UUID id;
    private String username;
}
