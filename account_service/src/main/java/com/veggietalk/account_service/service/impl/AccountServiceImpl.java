package com.veggietalk.account_service.service.impl;

import com.veggietalk.account_service.config.Producer;
import com.veggietalk.account_service.model.Account;
import com.veggietalk.account_service.persistence.AccountRepo;
import com.veggietalk.account_service.persistence.model.AccountEntity;
import com.veggietalk.account_service.service.AccountService;
import com.veggietalk.account_service.persistence.converters.AccountConverters;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepo accountRepo;

    @Autowired
    private Producer producer;

    @Override
    public Account saveAccount(Account account) {
        producer.verifyAccount(1L);
       return accountRepo.save(account);
    }

    @Override
    public Account updateAccount(Account account, Long userId) throws IllegalArgumentException{
        if (!account.getId().equals(userId)){
            throw new IllegalArgumentException("You do not have the right to update the following account");
        }
        return accountRepo.save(account);
    }

    @Override
    public Account findById(Long id){
        return accountRepo.findById(id);
    }

    @Override
    public void deleteAccount(Long id, Long userId) throws IllegalArgumentException{
        accountRepo.findById(id);
        if (!id.equals(userId)){
            throw new IllegalArgumentException("You do not have the right to delete this account");
        }
        accountRepo.delete(id);;
    }

    @Override
    public void addFollow(Long idFollower, Long idFollowing) throws Exception {
        Account follower = accountRepo.findById(idFollower);
        Account following = accountRepo.findById(idFollowing);

        following.addFollower(Account.builder().id(follower.getId()).build());
        accountRepo.save(following);
    }
}
