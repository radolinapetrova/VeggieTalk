package com.veggietalk.post_service.persistence;

import com.veggietalk.post_service.model.Category;
import com.veggietalk.post_service.model.DifficultyLevel;
import com.veggietalk.post_service.model.Post;
import com.veggietalk.post_service.persistence.model.PostEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepo {
    Post save(Post post);

    List<Post> getAllPosts();

    void deletePost(UUID id);
    Post findById(UUID id) throws IllegalArgumentException;


    List<Post> findAllRecipesByDifficulty(DifficultyLevel level);

    List<Post> findAllRecipesByCategory(Category category);

    List<Post> findAllByIngredientsContaining(List<String> ingredients);

    List<Post> findAllRecipes();

    void deleteByAccountId(UUID accountId);

    List<Post> findAllByAccountId(UUID accountId) throws IllegalArgumentException;


}
