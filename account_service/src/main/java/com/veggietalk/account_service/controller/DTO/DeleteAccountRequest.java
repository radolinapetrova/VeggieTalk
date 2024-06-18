package com.veggietalk.account_service.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class DeleteAccountRequest {
    private String jwtToken;
    private String username;
}
