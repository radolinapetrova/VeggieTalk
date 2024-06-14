package com.veggietalk.account_service.persistence;

import com.veggietalk.account_service.model.Account;
import com.veggietalk.account_service.persistence.model.AccountEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepo {
    Account save (Account account);

    void delete (UUID id);

    Account findById(UUID id);

    Account findByUsername(String username) throws IllegalArgumentException;
}
