package com.veggietalk.account_service.service;

import com.veggietalk.account_service.AccountServiceApplication;
import com.veggietalk.account_service.model.Account;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {
    Account saveAccount(Account account);

    Account updateAccount (Account account, Long user);

    void deleteAccount(Long id, Long userId);

    Account findById(Long id) throws IllegalArgumentException;

    public void addFollow(Long idFollower, Long idFollowing) throws Exception;
}
