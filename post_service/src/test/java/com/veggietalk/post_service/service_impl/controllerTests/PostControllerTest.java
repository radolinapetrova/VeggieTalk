package com.veggietalk.post_service.service_impl.controllerTests;

import com.veggietalk.post_service.controller.converters.RequestConverters;
import com.veggietalk.post_service.controller.requests.CreatePostRequest;
import com.veggietalk.post_service.domain.Post;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

    //HAPPY FLOW
    @Test
    public void testPostRequestConverter_shouldReturnPostObject(){
        //ARRANGE
        CreatePostRequest request = new CreatePostRequest();
        request.setDate("24-04-2024");
        request.setDescription("Descr");
        request.setUser_id(1L);

        //ACT
        Post post = RequestConverters.RequestConverter(request);

        //ASSERT
        assertEquals(request.getDescription(), post.getDescription());
        assertEquals(request.getDate(), post.getDate());
        assertEquals(request.getUser_id(), post.getUser_id());
    }
}
