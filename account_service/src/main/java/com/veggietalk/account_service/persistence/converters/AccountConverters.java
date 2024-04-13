package com.veggietalk.account_service.persistence.converters;

import com.veggietalk.account_service.model.Account;
import com.veggietalk.account_service.persistence.model.AccountEntity;

public class AccountConverters {
    public static AccountEntity AccountConverter(Account account){
        AccountEntity entity =  AccountEntity.builder()
                .email(account.getEmail())
                .bio(account.getBio())
                .build();

        if (account.getId() != null){
            entity.setId(account.getId());
        }

        if (account.getFollowing() != null){
            entity.setFollowing(account.getFollowing().stream().map(AccountConverters::AccountConverter).toList());
        }

        if (account.getFollowers() != null){
            entity.setFollowers(account.getFollowers().stream().map(AccountConverters::AccountConverter).toList());
        }

        return entity;
    }

    public static Account AccountEntityConverter(AccountEntity entity){
        Account account =  Account.builder()
                .email(entity.getEmail())
                .bio(entity.getEmail())
                .id(entity.getId())
                .build();

        if (entity.getFollowers() != null){
            account.setFollowers(entity.getFollowers().stream().map(AccountConverters::AccountEntityConverter).toList());
        }
        if (entity.getFollowing() != null){
            account.setFollowing(entity.getFollowing().stream().map(AccountConverters::AccountEntityConverter).toList());
        }

        return account;
    }
}
