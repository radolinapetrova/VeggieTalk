package com.veggietalk.auth_service.controller.converters;

import com.veggietalk.auth_service.controller.DTO.UserRequest;
import com.veggietalk.auth_service.controller.DTO.UserResponse;
import com.veggietalk.auth_service.model.User;

public class RequestConverters {

    public static User userRequestConverter(UserRequest request){
        return User.builder().email(request.getEmail()).password(request.getPassword()).build();
    }

    public static UserResponse userConverter(User user, Long account){
        return UserResponse.builder().id(user.getId()).accountId(account).email(user.getEmail()).build();
    }
}
