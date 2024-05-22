package com.veggietalk.post_service.persistence.model;

import com.veggietalk.post_service.model.Category;
import com.veggietalk.post_service.model.DifficultyLevel;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder(builderMethodName = "recipeBuilder")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recipe")
public class RecipeEntity{

    @Id
    @Column(name = "post_id")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "recipe_ingredients", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "ingredient", nullable = false)
    private List<String> ingredients;

    @Column(name = "difficulty_level")
    private DifficultyLevel difficultyLevel;

    private Category category;
}
