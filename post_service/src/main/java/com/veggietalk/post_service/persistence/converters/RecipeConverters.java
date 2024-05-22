package com.veggietalk.post_service.persistence.converters;

import com.veggietalk.post_service.model.Recipe;
import com.veggietalk.post_service.persistence.model.PostEntity;
import com.veggietalk.post_service.persistence.model.RecipeEntity;

public class RecipeConverters {

    public static Recipe RecipeEntityConverter(RecipeEntity entity){
        return Recipe.recipeBuilder()
                .difficultyLevel(entity.getDifficultyLevel())
                .category(entity.getCategory())
                .ingredients(entity.getIngredients())
                .build();
    }

    public static RecipeEntity RecipeConverter(Recipe recipe){

        return RecipeEntity.recipeBuilder()
                .id(recipe.getPostId())
                .ingredients(recipe.getIngredients())
                .difficultyLevel(recipe.getDifficultyLevel())
                .category(recipe.getCategory())
                .post(PostEntity.builder().id(recipe.getPostId()).build())
                .build();
    }
}
