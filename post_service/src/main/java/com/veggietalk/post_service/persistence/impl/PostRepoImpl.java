package com.veggietalk.post_service.persistence.impl;

import com.veggietalk.post_service.model.Category;
import com.veggietalk.post_service.model.DifficultyLevel;
import com.veggietalk.post_service.model.Post;
import com.veggietalk.post_service.persistence.DBRepos.PostDBRepo;
import com.veggietalk.post_service.persistence.PostRepo;
import com.veggietalk.post_service.persistence.converters.PostConverters;
import com.veggietalk.post_service.persistence.converters.RecipeConverters;
import com.veggietalk.post_service.persistence.model.PostEntity;
import com.veggietalk.post_service.persistence.model.RecipeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PostRepoImpl implements PostRepo {

    private final PostDBRepo postDBRepo;

    @Override
    public Post save(Post post) {
        // Convert Post to PostEntity
        PostEntity postEntity = PostConverters.PostConverter(post);
        postEntity = postDBRepo.saveAndFlush(postEntity);

        // Check if the Post has a Recipe
        if (post.getRecipe() != null) {
            RecipeEntity recipeEntity = RecipeConverters.RecipeConverter(post.getRecipe());
            recipeEntity.setPost(postEntity);
            postEntity.setRecipe(recipeEntity);
            postEntity = postDBRepo.save(postEntity);
        }

        return PostConverters.PostEntityConverter(postEntity);
    }


    @Override
    public List<Post> getAllPosts(int pageNumber) {
        Pageable firstPageable = PageRequest.of(pageNumber, 20);
        return postDBRepo.findAll(firstPageable).stream().map(PostConverters::PostEntityConverter).toList();
    }

    @Override
    public void deletePost(UUID id) {
        postDBRepo.deleteById(id);
    }

    @Override
    public Post findById(UUID id) throws IllegalArgumentException{
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

    @Override
    public List<UUID> deleteByAccountId(UUID accountId)throws IllegalArgumentException{
        List<PostEntity> posts = findByAccount(accountId);
        postDBRepo.deleteAll(posts);
        return posts.stream().map(PostEntity::getId).toList();
    }

    @Override
    public List<Post> findByAccountId(UUID account){
        List<PostEntity> posts = findByAccount(account);

        if (!posts.isEmpty()){
            return posts.stream().map(PostConverters::PostEntityConverter).toList();
        }
        throw new IllegalArgumentException("There are no posts created by this account");
    }


    private List<PostEntity> findByAccount(UUID account) {
        Optional<List<PostEntity>> entities = postDBRepo.findAllByAccountId(account);

        if (entities.isPresent() && !entities.get().isEmpty()) {
            return entities.get();
        } else {
            throw new IllegalArgumentException("No posts found for the provided account ID");
        }
    }


}
