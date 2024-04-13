package com.veggietalk.account_service.persistence;

import com.veggietalk.account_service.model.Account;
import com.veggietalk.account_service.persistence.model.AccountEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepo {
    Account save (Account account);

    void delete (Long id);

    Account findById(Long id);
}
