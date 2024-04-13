package com.veggietalk.post_service.service_impl.persistenceTests;

import com.veggietalk.post_service.model.Post;
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

import java.util.List;
import java.util.Optional;

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
        Post entity = Post.builder().date("27-12-2002").description("Descr").userId(1L).build();

        //ACT
        Post post = repository.save(entity);
        Post result = repository.findById(post.getId());

        //ASSERT
//        assertDoesNotThrow(IllegalArgumentException.class, () -> repository.findById(post.getId()));
        assertEquals(result.getId(), post.getId());
        assertEquals(result.getDate(), post.getDate());
        assertEquals(result.getUserId(), post.getUserId());
        assertEquals(result.getDescription(), post.getDescription());
    }

    @Test
    void testDeletePost(){
        //ARRANGE
        Post entity = Post.builder().date("27-12-2002").description("Descr").userId(1L).build();

        //ACT
        Post result = repository.save(entity);
        repository.deletePost(result.getId());

        //ASSERT
        assertThrows(IllegalArgumentException.class, () -> repository.findById(result.getId()));
    }

    @Test
    void testGetAllPosts(){
        //ARRANGE
        Post entity = Post.builder().date("27-12-2002").description("Descr1").userId(1L).build();
        Post entity2 = Post.builder().date("27-12-2002").description("Descr2").userId(2L).build();
        Post entity3 = Post.builder().date("27-12-2002").description("Descr3").userId(3L).build();

        //ACT
        repository.save(entity);
        repository.save(entity2);
        repository.save(entity3);
        List<Post> posts = repository.getAllPosts();

        //ASSERT
        assertEquals(posts.size(), 3);
        assertEquals(posts.getFirst().getUserId(), entity.getUserId());
        assertEquals(posts.getLast().getDescription(), entity3.getDescription());
        assertEquals(posts.get(1).getDescription(), entity2.getDescription());
    }
}
