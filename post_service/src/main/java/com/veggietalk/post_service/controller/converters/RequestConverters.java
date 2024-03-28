package com.veggietalk.post_service.controller.converters;

import com.veggietalk.post_service.controller.requests.CreatePostRequest;
import com.veggietalk.post_service.domain.Post;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RequestConverters {


    public static Post RequestConverter(CreatePostRequest request){
        Post post = new Post();
        String description = request.getDescription();
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Invalid description provided");
        }
        post.setDescription(request.getDescription());
        post.setUser_id(request.getUser_id());
        return post;
    }
}
