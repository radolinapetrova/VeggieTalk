package com.veggietalk.auth_service.controller;

import com.veggietalk.auth_service.controller.DTO.UserRequest;
import com.veggietalk.auth_service.controller.DTO.UserResponse;
import com.veggietalk.auth_service.controller.converters.RequestConverters;
import com.veggietalk.auth_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private UserService service;

    @GetMapping
    public ResponseEntity<String> login (@RequestBody UserRequest request) {

        boolean successfulLogin = service.login(request.getEmail(), request.getPassword());

        if (successfulLogin) {
            return ResponseEntity.status(200).body("You have successfully logged in you account");
        } else {
            return ResponseEntity.status(401).body("The password and/or email is/are incorrect");
        }
    }
}
