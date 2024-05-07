package com.veggietalk.auth_service.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UserRequest {
    private String email;
    private String password;
}
