package com.veggietalk.account_service.persistence.DBRepos;

import com.veggietalk.account_service.persistence.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountDBRepo extends JpaRepository<AccountEntity, UUID> {
    Optional<AccountEntity> findByUsername(String username);
}
