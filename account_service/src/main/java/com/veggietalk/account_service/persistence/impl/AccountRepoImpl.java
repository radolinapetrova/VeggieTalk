package com.veggietalk.account_service.persistence.impl;

import com.veggietalk.account_service.model.Account;
import com.veggietalk.account_service.persistence.AccountRepo;
import com.veggietalk.account_service.persistence.DBRepos.AccountDBRepo;
import com.veggietalk.account_service.persistence.converters.AccountConverters;
import com.veggietalk.account_service.persistence.model.AccountEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AccountRepoImpl implements AccountRepo {

    private final AccountDBRepo repo;


    @Override
    public Account save(Account account) {
        return AccountConverters.AccountEntityConverter(repo.save(AccountConverters.AccountConverter(account)));
    }

    @Override
    public void delete(UUID id) {
        repo.deleteById(id);
    }

    @Override
    public Account findById(UUID id) throws IllegalArgumentException{
        Optional<AccountEntity> entity = repo.findById(id);
        if (entity.isPresent()){
            return AccountConverters.AccountEntityConverter(entity.get());
        }
        else{
            throw new IllegalArgumentException("No such account exists");
        }
    }

    @Override
    public Account findByUsername(String username) throws IllegalArgumentException {
        Optional<AccountEntity> entity = repo.findByUsername(username);

        if(entity.isPresent()){
            return AccountConverters.AccountEntityConverter(entity.get());
        }
        throw new IllegalArgumentException("No such username exists");
    }

}
