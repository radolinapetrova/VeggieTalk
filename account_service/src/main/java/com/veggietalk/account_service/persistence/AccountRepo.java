package com.veggietalk.account_service.persistence;

import com.veggietalk.account_service.persistence.model.AccountEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepo {
    AccountEntity save (AccountEntity account);

    void delete (Long id);

    Optional<AccountEntity> findById(Long id);
}
