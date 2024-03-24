package com.veggietalk.post_service.controller;

import com.veggietalk.post_service.controller.converters.RequestConverters;
import com.veggietalk.post_service.controller.requests.CreatePostRequest;
import com.veggietalk.post_service.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping()
    public ResponseEntity createPost(@RequestBody CreatePostRequest request){
        return ResponseEntity.ok().body(postService.createPost(RequestConverters.RequestConverter(request)));
    }
}
