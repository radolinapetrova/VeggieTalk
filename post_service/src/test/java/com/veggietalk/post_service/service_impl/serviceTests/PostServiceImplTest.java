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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    //UNHAPPY FLOW
    @Test
    void testCreatePost_shouldThrowException_whenNoDescriptionIsProvided(){
        //ARRANGE
        Post post = Post.builder().userId(1L).build();
        Post post2 = Post.builder().description("").userId(1L).build();

        //ACT
        assertThrows(IllegalArgumentException.class, () -> service.createPost(post));
        assertThrows(IllegalArgumentException.class, () -> service.createPost(post2));

        //ASSERT
        verify(postRepo, never()).save(any(PostEntity.class));
    }

    //HAPPY FLOW
    @Test
    void testDeletePost_shouldSuccessfullyDeletePost(){
        //ARRANGE
        PostEntity entity = PostEntity.builder().build();
        when(postRepo.findById(any(Long.class))).thenReturn(Optional.ofNullable(entity));

        //ACT
        service.deletePost(1L, 1L);

        //ASSERT
        verify(postRepo, times(1)).deletePost(any(Long.class));

    }
    //UNHAPPY FLOW
    @Test
    void testDeletePost_shouldThrowException_whenPostDoesNotExist(){
        //ARRANGE
        when(postRepo.findById(any(Long.class))).thenReturn(Optional.empty());

        //ACT
        assertThrows(IllegalArgumentException.class, () -> service.deletePost(1L, 1L));

        //ASSERT
        verify(postRepo, never()).deletePost(any(Long.class));
    }


}
