package com.veggietalk.account_service.controller.converters;

import com.veggietalk.account_service.controller.DTO.AccountRequest;
import com.veggietalk.account_service.controller.DTO.AccountResponse;
import com.veggietalk.account_service.model.Account;

import java.util.ArrayList;

public class RequestConverter {

    public static AccountResponse accountConverter(Account account){
        AccountResponse response = AccountResponse.builder()
                .id(account.getId())
                .bio(account.getBio())
                .build();

        if (account.getFollowers() != null){
            account.setFollowers(new ArrayList<>());
        }
        if (account.getFollowing() != null){
            account.setFollowing(new ArrayList<>());
        }
        return response;
    }

    public static Account accountRequestConverter(AccountRequest request){
        Account account = Account.builder()
                .username(request.getUsername())
                .bio(request.getBio())
                .build();
        if (request.getId() != null ){
            account.setId(request.getId());
        }
        return account;
    }
}
