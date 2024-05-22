package com.veggietalk.post_service.model;

import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder(builderMethodName = "recipeBuilder")
public class Recipe extends Post{
    private List<String> ingredients;
    private DifficultyLevel difficultyLevel;
    private Category category;
    private Long postId;

}
