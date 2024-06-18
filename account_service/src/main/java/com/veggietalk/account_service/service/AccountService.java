package com.veggietalk.account_service.service;

import com.veggietalk.account_service.model.Account;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.IllegalFormatPrecisionException;
import java.util.UUID;

@Service
public interface AccountService {
    Account saveAccount(Account account) throws IllegalArgumentException;

    Account updateAccount (Account account, String username);

    void deleteAccount(String jwt, String username) throws ParseException;
    void deleteByUser(String username) throws IllegalArgumentException;

    Account findById(UUID id) throws IllegalArgumentException;

    void addFollow(UUID idFollower, UUID idFollowing) throws Exception;

    Account findByUsername(String token) throws ParseException, IllegalArgumentException;
}
