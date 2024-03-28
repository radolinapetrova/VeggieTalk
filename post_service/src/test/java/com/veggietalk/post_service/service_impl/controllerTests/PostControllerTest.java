package com.veggietalk.post_service.service_impl.controllerTests;

import com.veggietalk.post_service.controller.PostController;
import com.veggietalk.post_service.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

    @Autowired
    private PostController controller;

    @MockBean
    private PostService service;

    
}
