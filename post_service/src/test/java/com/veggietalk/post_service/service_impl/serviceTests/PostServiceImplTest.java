package com.veggietalk.post_service.service_impl.serviceTests;

import com.veggietalk.post_service.model.Post;
import com.veggietalk.post_service.persistence.PostRepo;
import com.veggietalk.post_service.persistence.model.PostEntity;
import com.veggietalk.post_service.persistence.converters.PostConverters;
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
import java.util.Optional;

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

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    //HAPPY FLOW
    @Test
    void testCreatePost_shouldReturnCreatedPostDetails(){
        //ARRANGE
        Post post = Post.builder().description("Descr").userId(1L).build();
        Post entity = Post.builder().id(1L).date(currentDate.format(formatter)).description("Descr").userId(1L).build();
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
        Post post = Post.builder().userId(1L).build();
        Post post2 = Post.builder().description("").userId(1L).build();

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
        Long id = 1L;
        Long user = 1L;
        when(postRepo.findById(any(Long.class))).thenReturn(Post.builder().userId(id).build());

        //ACT
        service.deletePost(id, user);

        //ASSERT
        verify(postRepo, times(1)).deletePost(any(Long.class));

    }
    //UNHAPPY FLOW
    @Test
    void testDeletePost_shouldThrowException_whenPostDoesNotExist(){
        //ARRANGE
        doThrow(IllegalArgumentException.class).when(postRepo).findById(any(Long.class));

        //ACT
        assertThrows(IllegalArgumentException.class, () -> service.deletePost(1L, 1L));

        //ASSERT
        verify(postRepo, never()).deletePost(any(Long.class));
    }

    //HAPPY FLOW
    @Test
    void testGetAllPosts_shouldReturnAllExistingPosts(){
        //ARRANGE
        List<Post> posts = List.of(
                Post.builder().id(1L).date(currentDate.format(formatter)).description("Descr").userId(1L).build(),
                Post.builder().id(2L).date(currentDate.format(formatter)).description("Descr2").userId(2L).build(),
                Post.builder().id(3L).date(currentDate.format(formatter)).description("Descr3").userId(3L).build()
        );
        when(postRepo.getAllPosts()).thenReturn(posts);

        //ACT
        List<Post> result = service.getAllPosts();

        //ASSERT
        verify(postRepo, times(1)).getAllPosts();
        assertEquals(1L, result.getFirst().getId());
        assertEquals("Descr2", result.get(1).getDescription());
        assertEquals(3L, result.get(2).getUserId());
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
