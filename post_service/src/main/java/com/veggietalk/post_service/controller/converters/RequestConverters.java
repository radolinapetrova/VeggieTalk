package com.veggietalk.post_service.controller.converters;

import com.veggietalk.post_service.controller.requests.CreatePostRequest;
import com.veggietalk.post_service.domain.Post;

public class RequestConverters {
    public static Post RequestConverter(CreatePostRequest request){
        return Post.builder().user_id(request.getUser_id()).date(request.getDate())
                .description(request.getDescription()).build();
    }
}
