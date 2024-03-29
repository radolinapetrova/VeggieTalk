package com.veggietalk.post_service.service_impl.controllerTests;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {
//
//    @InjectMocks
//    private PostService service;
//
//
//    @Mock
//    private MockMvc mockMvc;
//
//    @Test
//    void createPost() throws Exception {
//        // ARRANGE
//        CreatePostRequest request = CreatePostRequest.builder().description("Descr").user_id(1L).build();
//        CreatePostResponse response = CreatePostResponse.builder().date("28-03-2024").description(request.getDescription()).user_id(request.getUser_id()).build();
//        String content = new ObjectMapper().writeValueAsString(request);
//        String expected = new ObjectMapper().writeValueAsString(response);
//
//        // ACT
//        when(service.createPost(any(Post.class))).thenReturn(new Post(1L, "28-03-2024", request.getUser_id(), request.getDescription()));
//
//        // ASSERT
//        mockMvc.perform(post("/posts")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(content))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json"))
//                .andExpect(content().json(expected));
//    }
}
