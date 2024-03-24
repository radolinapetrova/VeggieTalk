package com.veggietalk.post_service.service_impl;

import com.veggietalk.post_service.domain.Post;
import com.veggietalk.post_service.persistence.PostRepo;
import com.veggietalk.post_service.service.impl.PostServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {
    @Mock
    private PostRepo postRepo;

    @InjectMocks
    private PostServiceImpl service;

    //HAPPY FLOW
    @Test
    void testCreatePost_shouldReturnCreatedPostDetails(){
        //ARRANGE
        Post post = Post.builder().id(1L).description("Descr").user_id(1L).date("24-03-2024").build();

        //ACT
        when(postRepo.save(any(Post.class))).thenReturn(post);
        Post result = service.createPost(post);

        //ASSERT
        assertEquals(post, result);
        verify(postRepo, times(1)).save(any(Post.class));
    }

    @Test
    void testCreatePost_whenSaveFails_ShouldThrowException() {
        // ARRANGE
        Post post = Post.builder().id(1L).description("Descr").user_id(1L).date("24-03-2024").build();
        when(postRepo.save(any(Post.class))).thenThrow(new RuntimeException("Failed to save post"));

        // ACT and ASSERT
        Assertions.assertThrows(RuntimeException.class, () -> {
            service.createPost(post);
        });
        verify(postRepo, times(1)).save(any(Post.class));
    }

}
