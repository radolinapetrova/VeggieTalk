package com.veggietalk.account_service.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class AccountResponse {
    private Long id;
    private String email;
    private String bio;
}
