package com.veggietalk.post_service.service_impl.persistenceTests;

import com.veggietalk.post_service.model.Category;
import com.veggietalk.post_service.model.DifficultyLevel;
import com.veggietalk.post_service.model.Post;
import com.veggietalk.post_service.model.Recipe;
import com.veggietalk.post_service.persistence.impl.PostRepoImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
public class PostRepoImplTest {

    @Container
    static PostgreSQLContainer container = new PostgreSQLContainer(DockerImageName.parse("postgres"));

    @Autowired
    private PostRepoImpl repository;

    @DynamicPropertySource
    static void setProperties (DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }


    List<Post> getDummyRecipes(){
        Post entity = Post.builder()
                .date("2002-12-27")
                .description("Descr1")
                .accountId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"))
                .recipe(Recipe.recipeBuilder()
                        .difficultyLevel(DifficultyLevel.EASY)
                        .ingredients(new ArrayList<String>() {{
                            add("hate");
                            add("spite");
                        }})
                        .category(Category.BREAKFAST)
                        .build())
                .build();
        Post entity2 = Post.builder()
                .date("2002-12-27")
                .description("Descr1")
                .accountId(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"))
                .recipe(Recipe.recipeBuilder()
                        .difficultyLevel(DifficultyLevel.EASY)
                        .ingredients(new ArrayList<String>() {{
                            add("cheese");
                            add("hate");
                            add("girl boss");
                            add("spite");
                        }})
                        .category(Category.DINNER)
                        .build())
                .build();
        Post entity3 = Post.builder()
                .date("2002-12-27")
                .description("Descr1")
                .accountId(UUID.fromString("e3b0c442-98fc-1c14-9af4-8b2b2ef8b6cd"))
                .recipe(Recipe.recipeBuilder()
                        .difficultyLevel(DifficultyLevel.HARD)
                        .ingredients(new ArrayList<String>() {{
                            add("gaslight");
                            add("girl boss");
                        }})
                        .category(Category.LUNCH)
                        .build())
                .build();

        Post entity4 = Post.builder().date("2002-12-27").description("Descr1").accountId(UUID.fromString("d94a41d5-fba4-4fc4-8000-20fd6c8c5f89")).build();
        Post entity5 = Post.builder().date("2002-12-27").description("Descr2").accountId(UUID.fromString("7e57d004-2b97-0e7a-b45f-5387367791cd")).build();
        Post entity6 = Post.builder().date("2002-12-27").description("Descr3").accountId(UUID.fromString("7e57d004-2b97-0e7a-b45f-5387368891cd")).build();

        List<Post> posts = List.of(entity, entity2, entity3, entity4, entity5, entity6);

        posts.forEach(repository::save);

        return posts;
    }


    @Test
    void testCreatePost(){
        //ARRANGE
        Post entity = Post.builder().date("2002-12-27").description("Descr").accountId(UUID.fromString("123e4567-e89b-12d3-b456-426614174000")).build();

        //ACT
        Post result = repository.save(entity);

        //ASSERT
        assertNotNull(result.getId());
        assertEquals(result.getDate(), entity.getDate());
        assertEquals(result.getDescription(), entity.getDescription());
        assertEquals(result.getAccountId(), entity.getAccountId());
    }


    @Test
    void testFindPostById(){
        //ARRANGE
        Post post = Post.builder().date("2002-12-27").description("Descr").accountId(UUID.fromString("456e4567-e89b-12d3-a456-426614174000")).build();

        //ACT
        Post entity = repository.save(post);
        Post result = repository.findById(entity.getId());

        //ASSERT
//        assertDoesNotThrow(IllegalArgumentException.class, () -> repository.findById(post.getId()));
        assertEquals(result.getId(), entity.getId());
        assertEquals(result.getDate(), post.getDate());
        assertEquals(result.getAccountId(), post.getAccountId());
        assertEquals(result.getDescription(), post.getDescription());
    }

    @Test
    void testDeletePost(){
        //ARRANGE
        Post result = Post.builder().id(UUID.fromString("123e4558-e89b-12d3-a456-426614174000")).date("2002-12-27").description("Descr").accountId(UUID.fromString("789e4567-e89b-12d3-a456-426614174000")).build();

        //ACT
        repository.deletePost(result.getId());

        //ASSERT
        assertThrows(IllegalArgumentException.class, () -> repository.findById(result.getId()));
    }


    @Test
    void testDeleteByAccountId(){
        //ARRANGE
        Post result = Post.builder().date("2002-12-27").description("Descr").accountId(UUID.fromString("789e4567-e89b-12d3-a456-426614165000")).build();

        //ACT
        Post post = repository.save(result);
        repository.deleteByAccountId(UUID.fromString("789e4567-e89b-12d3-a456-426614165000"));

        //ASSERT
        assertThrows(IllegalArgumentException.class, () -> repository.findById(post.getId()));

    }

    @Test
    void testDeleteShouldThrowException(){
        //ARRANGE
        Post result = Post.builder().date("2002-12-27").description("Descr").accountId(UUID.fromString("789e4567-e89b-12d3-a456-426614165000")).build();

        //ACT
        repository.save(result);

        //ASSERT
        assertThrows(IllegalArgumentException.class, () -> repository.deleteByAccountId(UUID.fromString("879e4567-e89b-12d3-a456-426614165000")));
    }



    @Test
    void testGetAllPosts(){
        //ARRANGE
        List<Post> posts = getDummyRecipes();

        //ACT
        List<Post> results = repository.getAllPosts(0);

        //ASSERT
        assertEquals(results.size(), 12);
        assertEquals(results.getFirst().getAccountId(), posts.getFirst().getAccountId());
        assertEquals(results.getLast().getDescription(), posts.getLast().getDescription());
        assertEquals(results.get(1).getDescription(), posts.get(1).getDescription());
    }


    @Test
    void testGetRecipesByDifficultyLevel(){
        //ARRANGE
        List<Post> posts = getDummyRecipes()
                .stream()
                .filter(post -> post.getRecipe() != null)
                .toList();
        //ACT
        List<Post> result =repository.findAllRecipesByDifficulty(DifficultyLevel.EASY);

        //ASSERT
        assertEquals(result.size(), 6);
        assertEquals(result.getFirst().getRecipe().getDifficultyLevel(), DifficultyLevel.EASY);
        assertEquals(result.getLast().getRecipe().getDifficultyLevel(), DifficultyLevel.EASY);
    }

    @Test
    void testGetRecipesByCategory(){
        //ARRANGE
        List<Post> posts = getDummyRecipes()
                .stream()
                .filter(post -> post.getRecipe() != null)
                .toList();

        //ACT
        List<Post> result = repository.findAllRecipesByCategory(Category.DINNER);

        //ASSERT
        assertEquals(result.size(), 1);
        assertEquals(result.getFirst().getRecipe().getCategory(), Category.DINNER);
    }


}
