package com.veggietalk.post_service.service_impl.serviceTests;

import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
public class PostServiceTests {
    PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("");
}
