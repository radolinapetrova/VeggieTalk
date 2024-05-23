package com.veggietalk.auth_service.service.impl;

import com.veggietalk.auth_service.model.User;
import com.veggietalk.auth_service.persistence.UserRepo;
import com.veggietalk.auth_service.persistence.impl.PasswordHasher;
import com.veggietalk.auth_service.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;


    @Override
    public User register(User user){
        String salt = PasswordHasher.generateSalt();
        String encrypted = PasswordHasher.encryptPassword(user.getPassword(), salt);
        user.setSalt(salt);
        user.setPassword(encrypted);

        return userRepo.saveUser(user);
    }

    @Override
    public boolean login (String email, String password){
        User user = userRepo.findByEmail(email);

        String providedPass = PasswordHasher.encryptPassword(password, user.getSalt());

        return providedPass.equals(user.getPassword());
    }

    public boolean deleteAccount(){
        return true;
    }
}
