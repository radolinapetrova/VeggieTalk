package com.veggietalk.post_service.service_impl.controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.veggietalk.post_service.controller.DTO.DeletePostRequest;
import com.veggietalk.post_service.controller.DTO.PostRequest;
import com.veggietalk.post_service.controller.DTO.PostResponse;
import com.veggietalk.post_service.controller.PostController;
import com.veggietalk.post_service.controller.converters.RequestConverters;
import com.veggietalk.post_service.model.Category;
import com.veggietalk.post_service.model.DifficultyLevel;
import com.veggietalk.post_service.model.Post;
import com.veggietalk.post_service.model.Recipe;
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
import java.util.UUID;

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
        PostRequest request = new PostRequest(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"), "Descr", List.of(""), Category.DINNER, DifficultyLevel.EASY);

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
        post.setId(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
        post.setDescription("Descr");
        post.setUserId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
        post.setDate("2002-12-27");

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
        PostRequest request = PostRequest.builder().description("Descr").userId(UUID.fromString("550e8400-e29b-41d4-a716-446655440000")).build();
        PostResponse response = PostResponse.builder().id(UUID.fromString("123e4567-e89b-12d3-a456-426614174000")).date("2024-03-27").description(request.getDescription()).userId(request.getUserId()).build();
        String content = new ObjectMapper().writeValueAsString(request);
        String expected = new ObjectMapper().writeValueAsString(response);

        // ACT
        when(service.createPost(any(Post.class))).thenReturn(new Post(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"), "2024-03-27", request.getUserId(), request.getDescription(), Recipe.recipeBuilder().build()));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/posts")
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
        PostRequest request = PostRequest.builder().userId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000")).build();
        String content = new ObjectMapper().writeValueAsString(request);

        // ACT
        when(service.createPost(any(Post.class))).thenThrow(IllegalArgumentException.class);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/posts")
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
        DeletePostRequest request = DeletePostRequest.builder().id(UUID.fromString("123e4567-e89b-12d3-a456-426614174000")).userId(UUID.fromString("123e4567-e89b-12d3-a456-426494174000")).build();
        String content = new ObjectMapper().writeValueAsString(request);

        // ACT
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/posts")
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
        DeletePostRequest request = DeletePostRequest.builder().id(UUID.fromString("123e4567-e89b-12d3-a456-426614174000")).userId(UUID.fromString("123e4568-e89b-12d3-a456-426614174000")).build();
        String content = new ObjectMapper().writeValueAsString(request);

        // ACT
        doThrow(IllegalArgumentException.class).when(service).deletePost(any(UUID.class), any(UUID.class));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/posts")
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
        posts.add(Post.builder().id(UUID.fromString("7e57d004-2b97-0e7a-b45f-5387367791cd")).description("Descr1").date("2002-12-27").userId(UUID.fromString("7e57d004-2b97-0e7a-b45f-5387367791cd")).build());
        posts.add(Post.builder().id(UUID.fromString("d94a41d5-fba4-4fc4-8000-20fd6c8c5f89")).description("Descr2").date("2002-12-27").userId(UUID.fromString("d94a41d5-fba4-4fc4-8000-20fd6c8c5f89")).build());
        posts.add(Post.builder().id(UUID.fromString("550e8400-e29b-41d4-a716-446655440000")).description("Descr3").date("2002-12-27").userId(UUID.fromString("550e8400-e29b-41d4-a716-446655440000")).build());
        List<PostResponse> result = posts.stream().map(RequestConverters::PostConverter).toList();
        String expected = (new ObjectMapper()).writeValueAsString(result);

        //ACT
        when(service.getAllPosts()).thenReturn(posts);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/posts")
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
