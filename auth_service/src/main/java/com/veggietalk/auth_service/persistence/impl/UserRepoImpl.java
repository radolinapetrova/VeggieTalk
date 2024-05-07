package com.veggietalk.auth_service.persistence.impl;

import com.veggietalk.auth_service.model.User;
import com.veggietalk.auth_service.persistence.DBRepo.UserDBRepo;
import com.veggietalk.auth_service.persistence.UserRepo;
import com.veggietalk.auth_service.persistence.converters.UserConverters;
import com.veggietalk.auth_service.persistence.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepoImpl implements UserRepo {

    private final UserDBRepo userRepo;

    @Override
    public User findByEmail(String email) {
        Optional<UserEntity> entity = userRepo.findUserEntityByEmail(email);
        if (entity.isEmpty()){
            throw new IllegalArgumentException("The provided email is invalid.");
        }
        return UserConverters.UserEntityConverter(entity.get());
    }

    @Override
    public User saveUser(User user){
        return UserConverters.UserEntityConverter(userRepo.save(UserConverters.UserConverter(user)));
    }

}
