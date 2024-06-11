package com.veggietalk.account_service.service;

import com.veggietalk.account_service.AccountServiceApplication;
import com.veggietalk.account_service.model.Account;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface AccountService {
    Account saveAccount(Account account);

    Account updateAccount (Account account, String user);

    void deleteAccount(UUID id, String userId);

    Account findById(UUID id) throws IllegalArgumentException;

    void addFollow(UUID idFollower, UUID idFollowing) throws Exception;
}
