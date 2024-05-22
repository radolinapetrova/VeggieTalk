package com.veggietalk.post_service.service_impl.persistenceTests;

import com.veggietalk.post_service.model.Category;
import com.veggietalk.post_service.model.DifficultyLevel;
import com.veggietalk.post_service.model.Post;
import com.veggietalk.post_service.model.Recipe;
import com.veggietalk.post_service.persistence.impl.PostRepoImpl;
import com.veggietalk.post_service.persistence.model.PostEntity;
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
import java.util.Optional;
import java.util.stream.Collectors;

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
                .date("27-12-2002")
                .description("Descr1")
                .userId(1L)
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
                .date("27-12-2002")
                .description("Descr1")
                .userId(1L)
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
                .date("27-12-2002")
                .description("Descr1")
                .userId(1L)
                .recipe(Recipe.recipeBuilder()
                        .difficultyLevel(DifficultyLevel.HARD)
                        .ingredients(new ArrayList<String>() {{
                            add("gaslight");
                            add("girl boss");
                        }})
                        .category(Category.LUNCH)
                        .build())
                .build();

        Post entity4 = Post.builder().date("27-12-2002").description("Descr1").userId(1L).build();
        Post entity5 = Post.builder().date("27-12-2002").description("Descr2").userId(2L).build();
        Post entity6 = Post.builder().date("27-12-2002").description("Descr3").userId(3L).build();

        List<Post> posts = List.of(entity, entity2, entity3, entity4, entity5, entity6);

        posts.forEach(repository::save);

        return posts;
    }


    @Test
    void testCreatePost(){
        //ARRANGE
        Post entity = Post.builder().date("27-12-2002").description("Descr").userId(1L).build();

        //ACT
        Post result = repository.save(entity);

        //ASSERT
        assertNotNull(result.getId());
        assertEquals(result.getDate(), entity.getDate());
        assertEquals(result.getDescription(), entity.getDescription());
        assertEquals(result.getUserId(), entity.getUserId());
    }


    @Test
    void testFindPostById(){
        //ARRANGE
        Post post = Post.builder().date("27-12-2002").description("Descr").userId(1L).build();

        //ACT
        Post entity = repository.save(post);
        Post result = repository.findById(entity.getId());

        //ASSERT
//        assertDoesNotThrow(IllegalArgumentException.class, () -> repository.findById(post.getId()));
        assertEquals(result.getId(), entity.getId());
        assertEquals(result.getDate(), post.getDate());
        assertEquals(result.getUserId(), post.getUserId());
        assertEquals(result.getDescription(), post.getDescription());
    }

    @Test
    void testDeletePost(){
        //ARRANGE
        Post result = Post.builder().id(1L).date("27-12-2002").description("Descr").userId(1L).build();

        //ACT
        repository.deletePost(result.getId());

        //ASSERT
        assertThrows(IllegalArgumentException.class, () -> repository.findById(result.getId()));
    }

    @Test
    void testGetAllPosts(){
        //ARRANGE
        List<Post> posts = getDummyRecipes();

        //ACT
        List<Post> results = repository.getAllPosts();

        //ASSERT
        assertEquals(results.size(), 12);
        assertEquals(results.getFirst().getUserId(), posts.getFirst().getUserId());
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

//    @Test
//    void TestGetRecipesByIngredients(){
//        //ARRANGE
//        List<Post> posts = getDummyRecipes()
//                .stream()
//                .filter(post -> post.getRecipe() != null)
//                .toList();
//
//        //ACT
//        List<Post> result = repository.findAllByIngredientsContaining(List.of("hate", "spite"));
//
//        //ASSERT
//        assertEquals(result.size(), 2);
//        assertEquals(result.size(), posts.stream().map(post -> post.getRecipe().getIngredients().containsAll(List.of("hate", "spite"))).toList().size());
//        assertTrue(result.getFirst().getRecipe().getIngredients().contains("hate"));
//        assertTrue(result.getFirst().getRecipe().getIngredients().contains("spite"));
//    }

}
