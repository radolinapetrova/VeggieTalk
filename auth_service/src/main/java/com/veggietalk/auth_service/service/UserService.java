package com.veggietalk.auth_service.service;

import com.veggietalk.auth_service.model.User;

public interface UserService {
    public boolean login (String email, String password);
    public User register(User user);
}
