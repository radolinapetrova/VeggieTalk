package com.veggietalk.account_service.service;

import com.veggietalk.account_service.model.Account;
import org.springframework.stereotype.Service;

import java.util.IllegalFormatPrecisionException;
import java.util.UUID;

@Service
public interface AccountService {
    Account saveAccount(Account account) throws IllegalArgumentException;

    Account updateAccount (Account account, String username);

    void deleteAccount(UUID id, String username);

    Account findById(UUID id) throws IllegalArgumentException;

    void addFollow(UUID idFollower, UUID idFollowing) throws Exception;
}
