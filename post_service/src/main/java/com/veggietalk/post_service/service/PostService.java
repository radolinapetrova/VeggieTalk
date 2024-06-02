package com.veggietalk.post_service.service;

import com.veggietalk.post_service.model.Category;
import com.veggietalk.post_service.model.DifficultyLevel;
import com.veggietalk.post_service.model.Post;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public interface PostService {
    Post createPost(Post post) throws IllegalArgumentException;

    void deletePost(UUID id, UUID accountId, String role) throws IllegalArgumentException;

    List<Post> getAllPosts() throws NoSuchElementException;

    public List<Post> findAllRecipes();

    List<Post> findAllRecipesByDifficulty(DifficultyLevel level);
    List<Post> findAllRecipesByCategory(Category category);

    List<Post> findAllRecipesByIngredients(List<String> ingredients);

    void deleteByAccountId(UUID accountId) throws IllegalArgumentException;
}
