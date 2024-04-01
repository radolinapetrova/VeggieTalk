package com.veggietalk.account_service.service.converters;

import com.veggietalk.account_service.model.Account;
import com.veggietalk.account_service.persistence.model.AccountEntity;

public class AccountConverters {
    public static AccountEntity AccountConverter(Account account){
        AccountEntity entity =  AccountEntity.builder()
                .email(account.getEmail())
                .password(account.getPassword())
                .username(account.getUsername())
                .build();

        if (account.getId() != null){
            entity.setId(account.getId());
        }

        return entity;
    }

    public static Account AccountEntityConverter(AccountEntity entity){
        return Account.builder()
                .email(entity.getEmail())
                .username(entity.getUsername())
                .id(entity.getId()).build();
    }
}
