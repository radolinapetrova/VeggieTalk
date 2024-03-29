package com.veggietalk.post_service.controller.converters;

import com.veggietalk.post_service.controller.DTO.PostRequest;
import com.veggietalk.post_service.controller.DTO.PostResponse;
import com.veggietalk.post_service.model.Post;

public class RequestConverters {


    public static Post RequestConverter(PostRequest request){
        Post post = new Post();
        String description = request.getDescription();
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Invalid description provided");
        }
        post.setDescription(request.getDescription());
        post.setUserId(request.getUserId());
        return post;
    }

    public static PostResponse PostConverter(Post post){
        return new PostResponse(post.getId(), post.getUserId(), post.getDescription(), post.getDate());
    }
}
