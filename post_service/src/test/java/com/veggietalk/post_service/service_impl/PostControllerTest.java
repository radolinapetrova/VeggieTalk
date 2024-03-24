package com.veggietalk.post_service.service_impl;

import com.veggietalk.post_service.controller.PostController;
import com.veggietalk.post_service.controller.converters.RequestConverters;
import com.veggietalk.post_service.controller.requests.CreatePostRequest;
import com.veggietalk.post_service.domain.Post;
import com.veggietalk.post_service.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest
public class PostControllerTest {
    @Autowired
    private PostController postController;

    @MockBean
    private PostService postService;

    //HAPPY FLOW
    @Test
    public void testPostRequestConverter_shouldReturnPostObject(){
        //ARRANGE
        CreatePostRequest request = new CreatePostRequest("24-03-2024", 1L, "descr");

        //ACT
        Post post = RequestConverters.RequestConverter(request);

        //ASSERT
        assertEquals(request.getDescription(), post.getDescription());
        assertEquals(request.getDate(), post.getDate());
        assertEquals(request.getUser_id(), post.getUser_id());
    }
}
