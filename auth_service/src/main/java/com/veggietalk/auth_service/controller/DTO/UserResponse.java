package com.veggietalk.auth_service.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UserResponse {

    private String email;
    private Long id;
    private Long accountId;
}
