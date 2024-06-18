package com.veggietalk.account_service.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
public class AccountResponse {
    private UUID id;
    private String username;
    private String bio;
}
