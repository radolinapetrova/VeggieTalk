package com.veggietalk.account_service.controller.converters;

import com.veggietalk.account_service.controller.DTO.AccountRequest;
import com.veggietalk.account_service.controller.DTO.AccountResponse;
import com.veggietalk.account_service.model.Account;

public class RequestConverter {

    public static AccountResponse accountConverter(Account account){
        return AccountResponse.builder().email(account.getEmail()).id(account.getId()).bio(account.getBio()).build();
    }

    public static Account accountRequestConverter(AccountRequest request){
        return Account.builder().email(request.getEmail()).bio(request.getBio()).build();
    }
}
