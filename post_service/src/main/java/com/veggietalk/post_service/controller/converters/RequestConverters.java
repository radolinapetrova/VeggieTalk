package com.veggietalk.post_service.controller.converters;

import com.veggietalk.post_service.controller.DTO.PostRequest;
import com.veggietalk.post_service.controller.DTO.PostResponse;
import com.veggietalk.post_service.model.Post;
import com.veggietalk.post_service.model.Recipe;

public class RequestConverters {


    public static Post RequestConverter(PostRequest request){
        String description = request.getDescription();
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Invalid description provided");
        }

        Post post = Post.builder()
                .userId(request.getUserId())
                .description(request.getDescription())
                .build();

        if(request.getCategory() != null || request.getLevel() != null || request.getIngredients() != null){
            post.setRecipe(Recipe.recipeBuilder()
                    .difficultyLevel(request.getLevel())
                    .ingredients(request.getIngredients())
                    .category(request.getCategory())
                    .build());
        }
        return post;
    }

    public static PostResponse PostConverter(Post post){
        PostResponse response = PostResponse.builder()
                .id(post.getId())
                .date(post.getDate())
                .description(post.getDescription())
                .userId(post.getUserId()).build();

        if (post.getRecipe() != null){
            Recipe recipe = post.getRecipe();
            response.setCategory(recipe.getCategory());
            response.setIngredients(recipe.getIngredients());
            response.setLevel(recipe.getDifficultyLevel());
        }
        return response;
    }
}
