package com.veggietalk.account_service.service;

import com.veggietalk.account_service.AccountServiceApplication;
import com.veggietalk.account_service.model.Account;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface AccountService {
    Account saveAccount(Account account);

    Account updateAccount (Account account, UUID user);

    void deleteAccount(UUID id, UUID userId);

    Account findById(UUID id) throws IllegalArgumentException;

    public void addFollow(UUID idFollower, UUID idFollowing) throws Exception;
}
