package com.veggietalk.post_service.controller;

import com.veggietalk.post_service.controller.DTO.DeletePostRequest;
import com.veggietalk.post_service.controller.converters.RequestConverters;
import com.veggietalk.post_service.controller.DTO.PostRequest;
import com.veggietalk.post_service.controller.DTO.PostResponse;
import com.veggietalk.post_service.model.Post;
import com.veggietalk.post_service.service.PostService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/posts")
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
            postService.deletePost(request.getId(), request.getUserId());
            return ResponseEntity.status(200).body("Noice");
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(417).body(e.getMessage());
        }
    }

}
