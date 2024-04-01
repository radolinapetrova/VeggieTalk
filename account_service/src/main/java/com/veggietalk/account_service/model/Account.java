package com.veggietalk.account_service.model;

import lombok.*;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private Long id;
    private String email;
    private String password;
    private String username;

}
