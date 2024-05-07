package com.veggietalk.auth_service.persistence.DBRepo;

import com.veggietalk.auth_service.persistence.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDBRepo extends JpaRepository<UserEntity, Long> {
    public Optional<UserEntity> findUserEntityByEmail(String email);
}
