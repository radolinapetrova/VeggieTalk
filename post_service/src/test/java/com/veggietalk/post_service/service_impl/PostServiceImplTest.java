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


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceImplTest {
    @Mock
    private PostRepo postRepo;

    @InjectMocks
    private PostServiceImpl service;

    @Test
    public void testCreatePost_shouldReturnCreatedPostDetails(){
        //ARRANGE
        Post post = new Post();

        //ACT
        when(postRepo.save(any(Post.class))).thenReturn(post);
        Post result = service.createPost(new Post());

        //ASSERT
        Assertions.assertSame(post, result);
        verify(postRepo).save(any(Post.class));

    }
}
