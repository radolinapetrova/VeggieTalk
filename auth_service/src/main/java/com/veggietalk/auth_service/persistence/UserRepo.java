package com.veggietalk.auth_service.persistence;

import com.veggietalk.auth_service.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo {
    public User findByEmail(String email);

    public User saveUser(User user);

    public void deleteUser(Long id);

}
