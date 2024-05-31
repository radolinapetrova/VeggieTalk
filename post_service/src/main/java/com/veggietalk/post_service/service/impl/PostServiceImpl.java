package com.veggietalk.post_service.service.impl;

import com.veggietalk.post_service.model.Category;
import com.veggietalk.post_service.model.DifficultyLevel;
import com.veggietalk.post_service.model.Post;
import com.veggietalk.post_service.persistence.PostRepo;
import com.veggietalk.post_service.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepo postRepo;

    LocalDate currentDate = LocalDate.now();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Post createPost(Post post) throws IllegalArgumentException{
        if (post.getDescription() == null || post.getDescription().isEmpty()){
            throw new IllegalArgumentException("There must be a description given for the post!");
        }
        post.setDate(currentDate.format(formatter));
        return postRepo.save(post);
    }

    public void deletePost(UUID id, UUID accountId, String role) throws IllegalArgumentException{
        Post post = postRepo.findById(id);
        if (!Objects.equals(post.getAccountId(), accountId) && !Objects.equals(role, "ADMIN")){
            throw new IllegalArgumentException("You do not have the right to delete this post");
        }
        postRepo.deletePost(id);
    }

    public List<Post> getAllPosts() throws NoSuchElementException {
        List<Post> posts = postRepo.getAllPosts();
        if(!posts.isEmpty()){
            return posts;
        }
        throw new NoSuchElementException("There are no available posts");
    }


    @Override
    public List<Post> findAllRecipesByDifficulty(DifficultyLevel level) {
        return postRepo.findAllRecipesByDifficulty(level);
    }

    @Override
    public List<Post> findAllRecipesByCategory(Category category) {
        return postRepo.findAllRecipesByCategory(category);
    }

    @Override
    public List<Post> findAllRecipesByIngredients(List<String> ingredients) {
        return postRepo.findAllByIngredientsContaining(ingredients);
    }

    @Override
    public List<Post> findAllRecipes(){
        return postRepo.findAllRecipes();
    }


}
