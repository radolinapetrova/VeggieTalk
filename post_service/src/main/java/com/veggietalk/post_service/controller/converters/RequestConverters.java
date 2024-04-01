package com.veggietalk.post_service.controller.converters;

import com.veggietalk.post_service.controller.DTO.PostRequest;
import com.veggietalk.post_service.controller.DTO.PostResponse;
import com.veggietalk.post_service.model.Post;

public class RequestConverters {


    public static Post RequestConverter(PostRequest request){
        String description = request.getDescription();
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Invalid description provided");
        }
        return Post.builder().description(request.getDescription()).userId(request.getUserId()).build();


    }

    public static PostResponse PostConverter(Post post){
        return new PostResponse(post.getId(), post.getUserId(), post.getDescription(), post.getDate());
    }
}
