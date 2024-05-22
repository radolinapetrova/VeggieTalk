package com.veggietalk.post_service.persistence.impl;

import com.veggietalk.post_service.model.Category;
import com.veggietalk.post_service.model.DifficultyLevel;
import com.veggietalk.post_service.model.Post;
import com.veggietalk.post_service.model.Recipe;
import com.veggietalk.post_service.persistence.DBRepos.PostDBRepo;
import com.veggietalk.post_service.persistence.DBRepos.RecipeDBRepo;
import com.veggietalk.post_service.persistence.PostRepo;
import com.veggietalk.post_service.persistence.converters.PostConverters;
import com.veggietalk.post_service.persistence.converters.RecipeConverters;
import com.veggietalk.post_service.persistence.model.PostEntity;
import com.veggietalk.post_service.persistence.model.RecipeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepoImpl implements PostRepo {

    private final PostDBRepo postDBRepo;
    private final RecipeDBRepo recipeDBRepo;

    @Override
    public Post save(Post post) {
        // Convert Post to PostEntity
        PostEntity postEntity = PostConverters.PostConverter(post);
        postEntity = postDBRepo.saveAndFlush(postEntity);

        // Check if the Post has a Recipe
        if (post.getRecipe() != null) {
            RecipeEntity recipeEntity = RecipeConverters.RecipeConverter(post.getRecipe());
            recipeEntity.setPost(postEntity);
//            recipeEntity = recipeDBRepo.save(recipeEntity);
            postEntity.setRecipe(recipeEntity);
            postEntity = postDBRepo.save(postEntity);
        }

        return PostConverters.PostEntityConverter(postEntity);
    }


    @Override
    public List<Post> getAllPosts() {
        return postDBRepo.findAll().stream().map(PostConverters::PostEntityConverter).toList();
    }

    @Override
    public void deletePost(Long id) {
        postDBRepo.deleteById(id);
    }

    @Override
    public Post findById(Long id) throws IllegalArgumentException{
        Optional<PostEntity> entity = postDBRepo.findById(id);
        if(entity.isPresent()){
            return PostConverters.PostEntityConverter(entity.get());
        }
       else{
           throw new IllegalArgumentException("The selected post does not exist :(");
        }
    }

    @Override
    public List<Post> findAllRecipesByDifficulty(DifficultyLevel level) {
        return postDBRepo.findAllByRecipeDifficultyLevel(level).stream().map(PostConverters::PostEntityConverter).toList();
    }

    @Override
    public List<Post> findAllRecipesByCategory(Category category) {
        return postDBRepo.findAllByRecipeCategory(category).stream().map(PostConverters::PostEntityConverter).toList();
    }

    @Override
    public List<Post> findAllByIngredientsContaining(List<String> ingredients) {
        return postDBRepo.findAllByRecipeIngredientsContainingIgnoreCase(ingredients).stream().map(PostConverters::PostEntityConverter).toList();
    }

    @Override
    public List<Post> findAllRecipes(){
        return postDBRepo.findAllRecipes().stream().map(PostConverters::PostEntityConverter).toList();
    }

}
