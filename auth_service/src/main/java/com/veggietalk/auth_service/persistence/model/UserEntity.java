package com.veggietalk.auth_service.persistence.model;

import com.veggietalk.auth_service.model.Role;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String salt;
    @Enumerated(EnumType.STRING)
    private Role role;
}
