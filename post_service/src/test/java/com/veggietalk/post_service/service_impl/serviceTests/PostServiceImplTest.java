package com.veggietalk.post_service.service_impl.serviceTests;
import com.amazonaws.services.s3.AmazonS3;
import com.veggietalk.post_service.model.Category;
import com.veggietalk.post_service.model.DifficultyLevel;
import com.veggietalk.post_service.model.Post;
import com.veggietalk.post_service.model.Recipe;
import com.veggietalk.post_service.persistence.PostRepo;
import com.veggietalk.post_service.rabbitmq_config.Producer;
import com.veggietalk.post_service.service.impl.PostServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    private final int page = 0;

    //HAPPY FLOW
    @Test
    void testCreatePost_shouldReturnCreatedPostDetails(){
        //ARRANGE
        Post post = Post.builder().description("Descr").accountId(UUID.fromString("273e4567-e89b-12d3-a456-426614174000")).build();
        Post entity = Post.builder().id(UUID.fromString("123e4567-e89b-12d3-a456-426614174000")).date(currentDate.format(formatter)).description("Descr").accountId(UUID.fromString("123e4589-e89b-12d3-a456-426614174000")).build();
        when(postRepo.save(any(Post.class))).thenReturn(entity);

        //ACT
        Post result = service.createPost(post);

        //ASSERT
        assertEquals(entity.getId(), result.getId());
        assertEquals(entity.getAccountId(), result.getAccountId());
        verify(postRepo, times(1)).save(any(Post.class));
    }

    //UNHAPPY FLOW
    @Test
    void testCreatePost_shouldThrowException_whenNoDescriptionIsProvided(){
        //ARRANGE
        Post post = Post.builder().accountId(UUID.fromString("e3b0c442-98fc-1c14-9af4-8b2b2ef8b6cd")).build();
        Post post2 = Post.builder().description("").accountId(UUID.fromString("e3b0c442-98fc-1c14-9af4-8b2b2ef8b6cd")).build();

        //ACT
        assertThrows(IllegalArgumentException.class, () -> service.createPost(post));
        assertThrows(IllegalArgumentException.class, () -> service.createPost(post2));

        //ASSERT
        verify(postRepo, never()).save(any(Post.class));
    }


    //UNHAPPY FLOW
    @Test
    void testDeletePost_shouldThrowException_whenPostDoesNotExist(){
        //ARRANGE
        when(postRepo.findById(any(UUID.class))).thenThrow(new IllegalArgumentException());

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
                Post.builder().id(UUID.fromString("123e4567-e89b-12d3-a456-426614174000")).date(currentDate.format(formatter)).description("Descr").accountId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000")).build(),
                Post.builder().id(UUID.fromString("550e8400-e29b-41d4-a716-446655440000")).date(currentDate.format(formatter)).description("Descr2").accountId(UUID.fromString("550e8400-e29b-41d4-a716-446655440000")).build(),
                Post.builder().id(UUID.fromString("e3b0c442-98fc-1c14-9af4-8b2b2ef8b6cd")).date(currentDate.format(formatter)).description("Descr3").accountId(UUID.fromString("e3b0c442-98fc-1c14-9af4-8b2b2ef8b6cd")).build()
        );
        when(postRepo.getAllPosts(page)).thenReturn(posts);

        //ACT
        List<Post> result = service.getAllPosts(page);

        //ASSERT
        verify(postRepo, times(1)).getAllPosts(page);
        assertEquals(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"), result.get(0).getId());
        assertEquals("Descr2", result.get(1).getDescription());
        assertEquals(UUID.fromString("e3b0c442-98fc-1c14-9af4-8b2b2ef8b6cd"), result.get(2).getAccountId());
    }

    //UNHAPPY FLOW
    @Test
    void testGetAllPosts_shouldThrowException_whenNoPostsExist(){
        //ARRANGE
        List<Post> posts = new ArrayList<>();
        when(postRepo.getAllPosts(page)).thenReturn(posts);

        //ACT
        assertThrows(NoSuchElementException.class, () -> service.getAllPosts(page));

        //ASSERT
        verify(postRepo, times(1)).getAllPosts(page);
    }

    @Test
    void getAllRecipesByDifficulty(){
        //ARRANGE
        List<Post> posts = List.of(
                Post.builder().id(UUID.fromString("123e4567-e89b-12d3-a456-426614174000")).date(currentDate.format(formatter)).description("Descr").accountId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000")).recipe(Recipe.recipeBuilder().difficultyLevel(DifficultyLevel.HARD).category(Category.DINNER).ingredients(List.of("ffood")).build()).build(),
                Post.builder().id(UUID.fromString("550e8400-e29b-41d4-a716-446655440000")).date(currentDate.format(formatter)).description("Descr2").accountId(UUID.fromString("550e8400-e29b-41d4-a716-446655440000")).recipe(Recipe.recipeBuilder().difficultyLevel(DifficultyLevel.HARD).category(Category.DINNER).ingredients(List.of("ffood")).build()).build(),
                Post.builder().id(UUID.fromString("e3b0c442-98fc-1c14-9af4-8b2b2ef8b6cd")).date(currentDate.format(formatter)).description("Descr3").accountId(UUID.fromString("e3b0c442-98fc-1c14-9af4-8b2b2ef8b6cd")).recipe(Recipe.recipeBuilder().difficultyLevel(DifficultyLevel.HARD).category(Category.DINNER).ingredients(List.of("ffood")).build()).build()
        );
        //ACT
        when(postRepo.findAllRecipesByDifficulty(DifficultyLevel.HARD)).thenReturn(posts);
        List<Post> result = service.findAllRecipesByDifficulty(DifficultyLevel.HARD);

        //ASSERT
        verify(postRepo, times(1)).findAllRecipesByDifficulty(DifficultyLevel.HARD);
        assertEquals(result.get(1).getRecipe().getDifficultyLevel(), DifficultyLevel.HARD);
    }

    @Test
    void getAllRecipesByCategory(){
        //ARRANGE
        List<Post> posts = List.of(
                Post.builder().id(UUID.fromString("123e4567-e89b-12d3-a456-426614174000")).date(currentDate.format(formatter)).description("Descr").accountId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000")).recipe(Recipe.recipeBuilder().difficultyLevel(DifficultyLevel.HARD).category(Category.DINNER).ingredients(List.of("ffood")).build()).build(),
                Post.builder().id(UUID.fromString("550e8400-e29b-41d4-a716-446655440000")).date(currentDate.format(formatter)).description("Descr2").accountId(UUID.fromString("550e8400-e29b-41d4-a716-446655440000")).recipe(Recipe.recipeBuilder().difficultyLevel(DifficultyLevel.HARD).category(Category.DINNER).ingredients(List.of("ffood")).build()).build(),
                Post.builder().id(UUID.fromString("e3b0c442-98fc-1c14-9af4-8b2b2ef8b6cd")).date(currentDate.format(formatter)).description("Descr3").accountId(UUID.fromString("e3b0c442-98fc-1c14-9af4-8b2b2ef8b6cd")).recipe(Recipe.recipeBuilder().difficultyLevel(DifficultyLevel.HARD).category(Category.DINNER).ingredients(List.of("ffood")).build()).build()
        );
        //ACT
        when(postRepo.findAllRecipesByCategory(Category.DINNER)).thenReturn(posts);
        List<Post> result = service.findAllRecipesByCategory(Category.DINNER);

        //ASSERT
        verify(postRepo, times(1)).findAllRecipesByCategory(Category.DINNER);
        assertEquals(result.get(1).getRecipe().getCategory(), Category.DINNER);
    }
}
