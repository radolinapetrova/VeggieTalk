package com.veggietalk.auth_service.persistence.converters;

import com.veggietalk.auth_service.model.User;
import com.veggietalk.auth_service.persistence.model.UserEntity;

public class UserConverters {
    public static UserEntity UserConverter(User user){
        return UserEntity.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .salt(user.getSalt())
                .role(user.getRole())
                .build();
    }

    public static User UserEntityConverter(UserEntity entity){
        return User.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .salt(entity.getSalt())
                .role(entity.getRole())
                .build();
    }
}
