package com.veggietalk.post_service.service_impl.persistenceTests;

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
        PostEntity entity = PostEntity.builder().date("27-12-2002").description("Descr").user_id(1L).build();

        //ACT
        PostEntity result = repository.save(entity);

        //ASSERT
        assertNotNull(result.getId());
        assertEquals(result.getDate(), entity.getDate());
        assertEquals(result.getDescription(), entity.getDescription());
        assertEquals(result.getUser_id(), entity.getUser_id());
    }


    @Test
    void testFindPostById(){
        //ARRANGE
        PostEntity entity = PostEntity.builder().date("27-12-2002").description("Descr").user_id(1L).build();

        //ACT
        PostEntity post = repository.save(entity);
        Optional<PostEntity> result = repository.findById(post.getId());

        //ASSERT
        assertTrue(result.isPresent());
        assertEquals(result.get().getId(), post.getId());
        assertEquals(result.get().getDate(), post.getDate());
        assertEquals(result.get().getUser_id(), post.getUser_id());
        assertEquals(result.get().getDescription(), post.getDescription());
    }

    @Test
    void testDeletePost(){
        //ARRANGE
        PostEntity entity = PostEntity.builder().date("27-12-2002").description("Descr").user_id(1L).build();

        //ACT
        PostEntity result = repository.save(entity);
        repository.deletePost(result.getId());
        Optional<PostEntity> deleted = repository.findById(result.getId());

        //ASSERT
        assertTrue(deleted.isEmpty());
    }

    @Test
    void testGetAllPosts(){
        //ARRANGE
        PostEntity entity = PostEntity.builder().date("27-12-2002").description("Descr1").user_id(1L).build();
        PostEntity entity2 = PostEntity.builder().date("27-12-2002").description("Descr2").user_id(2L).build();
        PostEntity entity3 = PostEntity.builder().date("27-12-2002").description("Descr3").user_id(3L).build();

        //ACT
        repository.save(entity);
        repository.save(entity2);
        repository.save(entity3);
        List<PostEntity> posts = repository.getAllPosts();

        //ASSERT
        assertEquals(posts.size(), 3);
        assertEquals(posts.getFirst().getUser_id(), entity.getUser_id());
        assertEquals(posts.getLast().getDescription(), entity3.getDescription());
        assertEquals(posts.get(1).getDescription(), entity2.getDescription());
    }
}
