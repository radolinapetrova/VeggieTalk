package com.veggietalk.account_service.controller;

import com.veggietalk.account_service.controller.DTO.AccountRequest;
import com.veggietalk.account_service.controller.DTO.AccountResponse;
import com.veggietalk.account_service.controller.DTO.DeleteAccountRequest;
import com.veggietalk.account_service.controller.DTO.FollowRequest;
import com.veggietalk.account_service.controller.converters.RequestConverter;
import com.veggietalk.account_service.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AccountController {

    private final AccountService accountService;

    @PostMapping()
    public ResponseEntity<AccountResponse> createAccount(@RequestBody AccountRequest request){
        return ResponseEntity.ok().body(RequestConverter.accountConverter(accountService.saveAccount(RequestConverter.accountRequestConverter(request))));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAccount(@RequestBody DeleteAccountRequest request){
        try{
            accountService.deleteAccount(request.getId(), request.getUserId());
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
        return ResponseEntity.ok().body("Account deleted successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable(value = "id") UUID id){
        return ResponseEntity.ok().body(RequestConverter.accountConverter(accountService.findById(id)));
    }

    @PostMapping("/follow")
    public ResponseEntity<String> addFollow(@RequestBody FollowRequest request) throws Exception{
        try{
            accountService.addFollow(request.getFollower(), request.getFollowing());
            return ResponseEntity.ok().body("Successfully followed");
        }
        catch (Exception e){
            return ResponseEntity.status(200).body(e.getMessage());
        }

    }

    @PutMapping
    public ResponseEntity<AccountResponse> updateAccount(@RequestBody AccountRequest request){
        return ResponseEntity.ok().body(RequestConverter.accountConverter(accountService.updateAccount(RequestConverter.accountRequestConverter(request), request.getUserId())));
    }


}
