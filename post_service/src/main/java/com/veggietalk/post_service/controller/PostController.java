package com.veggietalk.post_service.controller;

import com.veggietalk.post_service.controller.DTO.DeletePostRequest;
import com.veggietalk.post_service.controller.DTO.RecipeIngredientsRequest;
import com.veggietalk.post_service.controller.converters.RequestConverters;
import com.veggietalk.post_service.controller.DTO.PostRequest;
import com.veggietalk.post_service.controller.DTO.PostResponse;
import com.veggietalk.post_service.model.Category;
import com.veggietalk.post_service.model.DifficultyLevel;
import com.veggietalk.post_service.model.Post;
import com.veggietalk.post_service.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {

    private final PostService postService;

    @PostMapping()
    public ResponseEntity<?> createPost(@RequestBody PostRequest request){
        try{
            Post created = postService.createPost(RequestConverters.RequestConverter(request));
            return ResponseEntity.status(201).body(RequestConverters.PostConverter(created));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity
                    .badRequest()
                    .body(Collections.singletonMap("Unsuccessful creation of post", e.getMessage()));
        }
    }

    @GetMapping()
    public ResponseEntity<List<PostResponse>> getAllPosts(){
        return ResponseEntity.ok().body(postService.getAllPosts().stream().map(RequestConverters::PostConverter).toList());
    }


    @DeleteMapping()
    public ResponseEntity<String> deletePost(@RequestBody DeletePostRequest request){
        try{
            postService.deletePost(request.getId(), request.getAccountId(), request.getRole());
            return ResponseEntity.status(200).body("Noice");
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(417).body(e.getMessage());
        }
    }

    @GetMapping("level/{level}")
    public ResponseEntity<List<PostResponse>> getRecipeByDifficulty(@PathVariable(value = "level") DifficultyLevel level){
        return ResponseEntity.ok().body(postService.findAllRecipesByDifficulty(level).stream().map(RequestConverters::PostConverter).toList());
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<PostResponse>> getRecipeByCategory(@PathVariable(value = "category")Category category){
        return ResponseEntity.ok().body(postService.findAllRecipesByCategory(category).stream().map(RequestConverters::PostConverter).toList());
    }

    @GetMapping("ingredients")
    public ResponseEntity<List<PostResponse>> getRecipeByIngredients(@RequestBody RecipeIngredientsRequest request){
        return ResponseEntity.ok().body(postService.findAllRecipesByIngredients(request.getIngredients()).stream().map(RequestConverters::PostConverter).toList());
    }

    @GetMapping("recipes")
    public ResponseEntity<List<PostResponse>> getAllRecipes(){
        return ResponseEntity.ok().body(postService.findAllRecipes().stream().map(RequestConverters::PostConverter).toList());
    }

    @DeleteMapping("account/{id}")
    public ResponseEntity<Object> deleteByAccount(@PathVariable(value = "id") UUID id){
        postService.deleteByAccountId(id);
        return ResponseEntity.ok().body("Lovely");
    }


}
