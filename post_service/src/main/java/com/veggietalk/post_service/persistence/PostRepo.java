package com.veggietalk.post_service.persistence;

import com.veggietalk.post_service.model.Post;
import com.veggietalk.post_service.persistence.model.PostEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepo {
    PostEntity save(PostEntity post);

    List<PostEntity> getAllPosts();

    void deletePost(Long id);
    
    Optional<PostEntity> findById(Long id) throws IllegalArgumentException;
}
