package com.veggietalk.post_service.service_impl.serviceTests;

import com.veggietalk.post_service.model.Post;
import com.veggietalk.post_service.persistence.PostRepo;
import com.veggietalk.post_service.persistence.model.PostEntity;
import com.veggietalk.post_service.service.converters.PostConverters;
import com.veggietalk.post_service.service.impl.PostServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {
    @Mock
    private PostRepo postRepo;

    @InjectMocks
    private PostServiceImpl service;

    LocalDate currentDate = LocalDate.now();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    //HAPPY FLOW
    @Test
    void testCreatePost_shouldReturnCreatedPostDetails(){
        //ARRANGE
        Post post = Post.builder().description("Descr").userId(1L).build();
        PostEntity entity = PostEntity.builder().id(1L).date(currentDate.format(formatter)).description("Descr").user_id(1L).build();
        when(postRepo.save(any(PostEntity.class))).thenReturn(entity);

        //ACT
        Post result = service.createPost(post);

        //ASSERT
        assertEquals(PostConverters.PostEntityConverter(entity), result);
        verify(postRepo, times(1)).save(any(PostEntity.class));
    }






}
