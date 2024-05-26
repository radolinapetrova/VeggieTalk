package com.veggietalk.post_service.model;

import lombok.*;

import java.util.List;
import java.util.UUID;


@Getter
@Setter
@Builder(builderMethodName = "recipeBuilder")
public class Recipe extends Post{
    private List<String> ingredients;
    private DifficultyLevel difficultyLevel;
    private Category category;
    private UUID postId;

}
