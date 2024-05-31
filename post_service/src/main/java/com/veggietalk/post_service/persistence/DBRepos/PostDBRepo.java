package com.veggietalk.post_service.persistence.DBRepos;

import com.veggietalk.post_service.model.Category;
import com.veggietalk.post_service.model.DifficultyLevel;
import com.veggietalk.post_service.persistence.model.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostDBRepo extends JpaRepository<PostEntity, UUID> {
    @Query("SELECT p FROM PostEntity p WHERE NOT EXISTS (SELECT r FROM RecipeEntity r WHERE p.id = r.id)")
    List<PostEntity> findAllWithoutRecipes();

    List<PostEntity> findAllByRecipeDifficultyLevel(DifficultyLevel level);

    List<PostEntity> findAllByRecipeCategory(Category category);

    @Query("SELECT p FROM PostEntity p INNER JOIN RecipeEntity r ON p.id = r.post.id WHERE LOWER(r.ingredients) IN (:ingredients)")
    List<PostEntity> findAllByRecipeIngredientsContainingIgnoreCase(@Param("ingredients") List<String> ingredients);

    @Query("SELECT p FROM PostEntity p INNER JOIN RecipeEntity r ON p.id = r.post.id")
    List<PostEntity> findAllRecipes();

    PostEntity deleteByAccountId(UUID accountId);
}
