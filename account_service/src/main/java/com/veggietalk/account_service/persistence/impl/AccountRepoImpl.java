package com.veggietalk.account_service.persistence.impl;

import com.veggietalk.account_service.persistence.AccountRepo;
import com.veggietalk.account_service.persistence.DBRepos.AccountDBRepo;
import com.veggietalk.account_service.persistence.model.AccountEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AccountRepoImpl implements AccountRepo {

    private final AccountDBRepo repo;


    @Override
    public AccountEntity save(AccountEntity account) {
        return repo.save(account);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public Optional<AccountEntity> findById(Long id) {
        return repo.findById(id);
    }

}
