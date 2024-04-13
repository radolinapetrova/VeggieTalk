package com.veggietalk.account_service.service.impl;

import com.veggietalk.account_service.model.Account;
import com.veggietalk.account_service.persistence.AccountRepo;
import com.veggietalk.account_service.persistence.model.AccountEntity;
import com.veggietalk.account_service.service.AccountService;
import com.veggietalk.account_service.persistence.converters.AccountConverters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepo accountRepo;

    @Override
    public Account saveAccount(Account account) {
       return accountRepo.save(account);
    }

    @Override
    public Account updateAccount(Account account) throws IllegalArgumentException{
        return accountRepo.save(account);
    }

    @Override
    public Account findById(Long id){
        return accountRepo.findById(id);
    }

    @Override
    public void deleteAccount(Long id, Long userId) throws IllegalArgumentException{
        accountRepo.findById(id);
        accountRepo.delete(id);;
    }

    @Override
    public void addFollow(Long idFollower, Long idFollowing){
        Account follower = accountRepo.findById(idFollower);
        Account following = accountRepo.findById(idFollowing);


        follower.getFollowing().add(Account.builder().id(following.getId()).build());
        following.getFollowers().add(Account.builder().id(follower.getId()).build());

        accountRepo.save(follower);
        accountRepo.save(following);
    }
}
