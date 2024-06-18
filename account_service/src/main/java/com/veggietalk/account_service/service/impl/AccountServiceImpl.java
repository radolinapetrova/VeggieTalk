package com.veggietalk.account_service.service.impl;

import com.nimbusds.jwt.JWTClaimsSet;
import com.veggietalk.account_service.config.Producer;
import com.veggietalk.account_service.model.Account;
import com.veggietalk.account_service.persistence.AccountRepo;
import com.veggietalk.account_service.service.AccountService;
import com.veggietalk.account_service.service.JwtDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepo accountRepo;

    @Autowired
    private Producer producer;

    @Override
    public Account saveAccount(Account account) throws IllegalArgumentException{
        try{
            accountRepo.findByUsername(account.getUsername());
        }
        catch (IllegalArgumentException e){
            return accountRepo.save(account);
        }
       throw new IllegalArgumentException("An account with the specified username already exists.");
    }

    @Override
    public Account updateAccount(Account account, String username) throws IllegalArgumentException{
        if (!account.getUsername().equals(username)){
            throw new IllegalArgumentException("You do not have the right to update the following account");
        }
        return accountRepo.save(account);
    }

    @Override
    public Account findById(UUID id){
        return accountRepo.findById(id);
    }

    @Override
    public void deleteByUser(String username) {
        Account acc = accountRepo.findByUsername(username);
        accountRepo.delete(acc.getId());
        producer.deleteAccount(acc.getId().toString());
    }

    @Override
    public void deleteAccount(String jwtToken, String username) throws IllegalArgumentException, ParseException {
        Account ac = accountRepo.findByUsername(username);
        JWTClaimsSet jwtClaims = JwtDecoder.decodeJwtToken(jwtToken);
        if (jwtClaims != null && jwtClaims.getStringClaim("preferred_username").equals(username)){
            accountRepo.delete(ac.getId());
            producer.deleteAccount(username);
            return;
        }
        throw new IllegalArgumentException("You do not have the right to delete this account");
    }

    @Override
    public void addFollow(UUID idFollower, UUID idFollowing) throws Exception {
        Account follower = accountRepo.findById(idFollower);
        Account following = accountRepo.findById(idFollowing);

        following.addFollower(Account.builder().id(follower.getId()).build());
        accountRepo.save(following);
    }

    @Override
    public Account findByUsername(String token) throws ParseException, IllegalArgumentException {
        JWTClaimsSet jwtClaims = JwtDecoder.decodeJwtToken(token);

        if (jwtClaims != null ){
            String username = jwtClaims.getStringClaim("preferred_username");
            return accountRepo.findByUsername(username);
        }
        throw new IllegalArgumentException("Don't be fucking rude");
    }
}
