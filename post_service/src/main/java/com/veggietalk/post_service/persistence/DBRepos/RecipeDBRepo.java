package com.veggietalk.post_service.persistence.DBRepos;

import com.veggietalk.post_service.model.Category;
import com.veggietalk.post_service.model.DifficultyLevel;
import com.veggietalk.post_service.persistence.model.PostEntity;
import com.veggietalk.post_service.persistence.model.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecipeDBRepo extends JpaRepository<RecipeEntity, UUID> {

}
