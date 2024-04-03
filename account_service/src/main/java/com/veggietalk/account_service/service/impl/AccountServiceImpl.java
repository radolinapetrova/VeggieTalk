package com.veggietalk.account_service.service.impl;

import com.veggietalk.account_service.model.Account;
import com.veggietalk.account_service.persistence.AccountRepo;
import com.veggietalk.account_service.service.AccountService;
import com.veggietalk.account_service.service.converters.AccountConverters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepo accountRepo;

    @Override
    public Account saveAccount(Account account) {
        return AccountConverters.AccountEntityConverter(accountRepo.save(AccountConverters.AccountConverter(account)));
    }

    @Override
    public Account updateAccount(Account account) {
        if (accountRepo.findById(account.getId()).isPresent()){
            return AccountConverters.AccountEntityConverter(accountRepo.save(AccountConverters.AccountConverter(account)));
        }
        else{
            throw new IllegalArgumentException("No such account exists");
        }
    }

    @Override
    public void deleteAccount(Long id, Long userId) throws IllegalArgumentException{
        if (accountRepo.findById(id).isPresent()){
            accountRepo.delete(id);
        }
        else{
            throw new IllegalArgumentException("No such account exists");
        }
    }
}
