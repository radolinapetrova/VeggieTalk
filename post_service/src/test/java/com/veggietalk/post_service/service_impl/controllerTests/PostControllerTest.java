package com.veggietalk.post_service.service_impl.controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.veggietalk.post_service.controller.DTO.PostRequest;
import com.veggietalk.post_service.controller.DTO.PostResponse;
import com.veggietalk.post_service.controller.PostController;
import com.veggietalk.post_service.controller.converters.RequestConverters;
import com.veggietalk.post_service.model.Post;
import com.veggietalk.post_service.service.PostService;
import com.veggietalk.post_service.service.converters.PostConverters;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = PostController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class PostControllerTest {

    @MockBean
    private PostService service;

    @Autowired
    private PostController controller;


    @Test
    void createPost_shouldSuccessfullyCreatePost() throws Exception {
        // ARRANGE
        PostRequest request = PostRequest.builder().description("Descr").userId(1L).build();
        PostResponse response = PostResponse.builder().id(1L).date("28-03-2024").description(request.getDescription()).userId(request.getUserId()).build();
        String content = new ObjectMapper().writeValueAsString(request);
        String expected = new ObjectMapper().writeValueAsString(response);

        // ACT
        when(service.createPost(RequestConverters.RequestConverter(request))).thenReturn(new Post(1L, "28-03-2024", request.getUserId(), request.getDescription()));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        //ASSERT
        MockMvcBuilders.standaloneSetup(controller)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(expected));
    }

    @Test
    void createPost_shouldThrowException_whenInvalidDataWasProvided() throws Exception{
        // ARRANGE
        PostRequest request = PostRequest.builder().userId(1L).build();
        String content = new ObjectMapper().writeValueAsString(request);

        // ACT
        when(service.createPost(any(Post.class))).thenThrow(IllegalArgumentException.class);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        //ASSERT
        MockMvcBuilders.standaloneSetup(controller)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
