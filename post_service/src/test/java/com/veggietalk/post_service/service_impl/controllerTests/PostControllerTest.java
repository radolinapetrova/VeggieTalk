package com.veggietalk.post_service.service_impl.controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.veggietalk.post_service.controller.DTO.DeletePostRequest;
import com.veggietalk.post_service.controller.DTO.PostRequest;
import com.veggietalk.post_service.controller.DTO.PostResponse;
import com.veggietalk.post_service.controller.PostController;
import com.veggietalk.post_service.controller.converters.RequestConverters;
import com.veggietalk.post_service.model.Post;
import com.veggietalk.post_service.service.PostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = PostController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class PostControllerTest {

    @MockBean
    private PostService service;

    @Autowired
    private PostController controller;


    @Test
    void testPostRequestConverter(){
        //ARRANGE
        PostRequest request = new PostRequest(1L, "Descr");

        //ACT
        Post result = RequestConverters.RequestConverter(request);

        //ASSERT
        assertEquals(result.getDescription(), request.getDescription());
        assertEquals(result.getUserId(), request.getUserId());
    }

    @Test
    void testPostResponseConverter(){
        //ARRANGE
        Post post = new Post();
        post.setUserId(1L);
        post.setDescription("Descr");
        post.setUserId(1L);
        post.setDate("27-12-2002");

        //ACT
        PostResponse response = RequestConverters.PostConverter(post);

        //ASSERT
        assertEquals(response.getDate(), post.getDate());
        assertEquals(response.getId(), post.getId());
        assertEquals(response.getUserId(), post.getUserId());
        assertEquals(response.getDescription(), post.getDescription());
    }



    @Test
    void createPost_shouldSuccessfullyCreatePost() throws Exception {
        // ARRANGE
        PostRequest request = PostRequest.builder().description("Descr").userId(1L).build();
        PostResponse response = PostResponse.builder().id(1L).date("28-03-2024").description(request.getDescription()).userId(request.getUserId()).build();
        String content = new ObjectMapper().writeValueAsString(request);
        String expected = new ObjectMapper().writeValueAsString(response);

        // ACT
        when(service.createPost(any(Post.class))).thenReturn(new Post(1L, "28-03-2024", request.getUserId(), request.getDescription()));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        //ASSERT
        MockMvcBuilders.standaloneSetup(controller)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(201))
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


    @Test
    void deletePost_shouldSuccessfullyDeletePost() throws Exception{
        // ARRANGE
        DeletePostRequest request = DeletePostRequest.builder().id(1L).userId(1L).build();
        String content = new ObjectMapper().writeValueAsString(request);

        // ACT
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        //ASSERT
        MockMvcBuilders.standaloneSetup(controller)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void deletePost_shouldReturnErrorMessage_whenPostDoesNotExist() throws Exception{
        // ARRANGE
        DeletePostRequest request = DeletePostRequest.builder().id(1L).userId(1L).build();
        String content = new ObjectMapper().writeValueAsString(request);

        // ACT
        doThrow(IllegalArgumentException.class).when(service).deletePost(any(Long.class), any(Long.class));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        //ASSERT
        MockMvcBuilders.standaloneSetup(controller)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(417));
    }

    @Test
    void testGetAllPosts_shouldReturnAllPosts() throws Exception{
        //ARRANGE
        List<Post> posts = new ArrayList<>();
        posts.add(Post.builder().id(1L).description("Descr1").date("27-12-2002").userId(1L).build());
        posts.add(Post.builder().id(2L).description("Descr2").date("27-12-2002").userId(2L).build());
        posts.add(Post.builder().id(3L).description("Descr3").date("27-12-2002").userId(3L).build());
        List<PostResponse> result = posts.stream().map(RequestConverters::PostConverter).toList();
        String expected = (new ObjectMapper()).writeValueAsString(result);

        //ACT
        when(service.getAllPosts()).thenReturn(posts);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/posts")
                .contentType(MediaType.APPLICATION_JSON);

        //ASSERT
        MockMvcBuilders.standaloneSetup(controller)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(expected));
    }

}
