package com.veggietalk.auth_service;

import com.veggietalk.auth_service.persistence.UserRepo;
import com.veggietalk.auth_service.service.PasswordHasher;
import com.veggietalk.auth_service.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testPasswordHasher(){
        String salt = PasswordHasher.generateSalt();
        String pass = "1234";
        String hashed = PasswordHasher.encryptPassword(pass, salt);

        assertNotEquals(pass, hashed);
    }
}
