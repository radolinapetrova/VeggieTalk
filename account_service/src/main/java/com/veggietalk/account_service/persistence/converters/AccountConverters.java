package com.veggietalk.account_service.persistence.converters;

import com.veggietalk.account_service.model.Account;
import com.veggietalk.account_service.persistence.model.AccountEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AccountConverters {
    public static AccountEntity AccountConverter(Account account) {
        AccountEntity entity = AccountEntity.builder()
                .email(account.getEmail())
                .build();

        if (account.getId() != null) {
            entity.setId(account.getId());
        }

        if (account.getBio() != null) {
            entity.setBio(account.getBio());
        } else {
            entity.setBio("");
        }

        if (account.getFollowing() != null) {
            entity.setFollowing(account.getFollowing().stream()
                    .map(acc -> AccountEntity.builder().id(acc.getId()).email(acc.getEmail()).build())
                    .collect(Collectors.toCollection(ArrayList::new)));
        } else {
            entity.setFollowing(new ArrayList<>());
        }

        if (account.getFollowers() != null) {
            entity.setFollowers(account.getFollowers().stream()
                    .map(acc -> AccountEntity.builder().id(acc.getId()).email(acc.getEmail()).build())
                    .collect(Collectors.toCollection(ArrayList::new)));
        } else {
            entity.setFollowers(new ArrayList<>());
        }

        return entity;
    }

    public static Account AccountEntityConverter(AccountEntity entity) {
        Account account = Account.builder()
                .email(entity.getEmail())
                .bio(entity.getBio())
                .id(entity.getId())
                .build();

        if (entity.getFollowers() != null) {
            account.setFollowers(entity.getFollowers().stream()
                    .map(acc -> Account.builder().id(acc.getId()).email(acc.getEmail()).build())
                    .collect(Collectors.toCollection(ArrayList::new)));
        } else {
            account.setFollowers(new ArrayList<>());
        }

        if (entity.getFollowing() != null) {
            account.setFollowing(entity.getFollowing().stream()
                    .map(acc -> Account.builder().id(acc.getId()).email(acc.getEmail()).build())
                    .collect(Collectors.toCollection(ArrayList::new)));
        } else {
            account.setFollowing(new ArrayList<>());
        }

        return account;
    }
}
