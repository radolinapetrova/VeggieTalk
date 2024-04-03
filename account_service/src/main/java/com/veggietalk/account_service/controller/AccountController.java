package com.veggietalk.account_service.controller;

import com.veggietalk.account_service.controller.DTO.AccountRequest;
import com.veggietalk.account_service.controller.DTO.AccountResponse;
import com.veggietalk.account_service.controller.DTO.DeleteAccountRequest;
import com.veggietalk.account_service.controller.converters.RequestConverter;
import com.veggietalk.account_service.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping()
    public ResponseEntity<AccountResponse> createAccount(AccountRequest request){
        return ResponseEntity.ok().body(RequestConverter.accountConverter(accountService.saveAccount(RequestConverter.accountRequestConverter(request))));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAccount(DeleteAccountRequest request){
        try{
            accountService.deleteAccount(request.getId(), request.getUserId());
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
        return ResponseEntity.ok().body("Account deleted successfully");
    }


}
