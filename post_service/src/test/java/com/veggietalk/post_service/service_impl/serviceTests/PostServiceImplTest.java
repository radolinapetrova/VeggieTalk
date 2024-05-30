package com.veggietalk.post_service.service_impl.serviceTests;

import com.veggietalk.post_service.model.Post;
import com.veggietalk.post_service.persistence.PostRepo;
import com.veggietalk.post_service.service.impl.PostServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {
    @Mock
    private PostRepo postRepo;

    @InjectMocks
    private PostServiceImpl service;

    LocalDate currentDate = LocalDate.now();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    //HAPPY FLOW
    @Test
    void testCreatePost_shouldReturnCreatedPostDetails(){
        //ARRANGE
        Post post = Post.builder().description("Descr").userId(UUID.fromString("273e4567-e89b-12d3-a456-426614174000")).build();
        Post entity = Post.builder().id(UUID.fromString("123e4567-e89b-12d3-a456-426614174000")).date(currentDate.format(formatter)).description("Descr").userId(UUID.fromString("123e4589-e89b-12d3-a456-426614174000")).build();
        when(postRepo.save(any(Post.class))).thenReturn(entity);

        //ACT
        Post result = service.createPost(post);

        //ASSERT
        assertEquals(entity.getId(), result.getId());
        assertEquals(entity.getUserId(), result.getUserId());
        verify(postRepo, times(1)).save(any(Post.class));
    }

    //UNHAPPY FLOW
    @Test
    void testCreatePost_shouldThrowException_whenNoDescriptionIsProvided(){
        //ARRANGE
        Post post = Post.builder().userId(UUID.fromString("e3b0c442-98fc-1c14-9af4-8b2b2ef8b6cd")).build();
        Post post2 = Post.builder().description("").userId(UUID.fromString("e3b0c442-98fc-1c14-9af4-8b2b2ef8b6cd")).build();

        //ACT
        assertThrows(IllegalArgumentException.class, () -> service.createPost(post));
        assertThrows(IllegalArgumentException.class, () -> service.createPost(post2));

        //ASSERT
        verify(postRepo, never()).save(any(Post.class));
    }

    //HAPPY FLOW
    @Test
    void testDeletePost_shouldSuccessfullyDeletePost(){
        //ARRANGE
        UUID id = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        UUID user = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        when(postRepo.findById(any(UUID.class))).thenReturn(Post.builder().userId(id).build());

        //ACT
        service.deletePost(id, user, "USER");

        //ASSERT
        verify(postRepo, times(1)).deletePost(any(UUID.class));

    }
    //UNHAPPY FLOW
    @Test
    void testDeletePost_shouldThrowException_whenPostDoesNotExist(){
        //ARRANGE
        doThrow(IllegalArgumentException.class).when(postRepo).findById(any(UUID.class));

        //ACT
        assertThrows(IllegalArgumentException.class, () -> service.deletePost(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"), UUID.fromString("123e4567-e89b-12d3-a456-426614174000"), "USER"));

        //ASSERT
        verify(postRepo, never()).deletePost(any(UUID.class));
    }

    //HAPPY FLOW
    @Test
    void testGetAllPosts_shouldReturnAllExistingPosts(){
        //ARRANGE
        List<Post> posts = List.of(
                Post.builder().id(UUID.fromString("123e4567-e89b-12d3-a456-426614174000")).date(currentDate.format(formatter)).description("Descr").userId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000")).build(),
                Post.builder().id(UUID.fromString("550e8400-e29b-41d4-a716-446655440000")).date(currentDate.format(formatter)).description("Descr2").userId(UUID.fromString("550e8400-e29b-41d4-a716-446655440000")).build(),
                Post.builder().id(UUID.fromString("e3b0c442-98fc-1c14-9af4-8b2b2ef8b6cd")).date(currentDate.format(formatter)).description("Descr3").userId(UUID.fromString("e3b0c442-98fc-1c14-9af4-8b2b2ef8b6cd")).build()
        );
        when(postRepo.getAllPosts()).thenReturn(posts);

        //ACT
        List<Post> result = service.getAllPosts();

        //ASSERT
        verify(postRepo, times(1)).getAllPosts();
        assertEquals(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"), result.getFirst().getId());
        assertEquals("Descr2", result.get(1).getDescription());
        assertEquals(UUID.fromString("e3b0c442-98fc-1c14-9af4-8b2b2ef8b6cd"), result.get(2).getUserId());
    }

    //UNHAPPY FLOW
    @Test
    void testGetAllPosts_shouldThrowException_whenNoPostsExist(){
        //ARRANGE
        List<Post>posts = new ArrayList<>();
        when(postRepo.getAllPosts()).thenReturn(posts);

        //ACT
        assertThrows(NoSuchElementException.class, () -> service.getAllPosts());

        //ASSERT
        verify(postRepo, times(1)).getAllPosts();
    }

}
