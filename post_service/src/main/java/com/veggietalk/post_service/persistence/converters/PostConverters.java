package com.veggietalk.post_service.persistence.converters;

import com.veggietalk.post_service.domain.Post;
import com.veggietalk.post_service.persistence.model.PostEntity;

public class PostConverters {
    public static Post PostEntityConverter(PostEntity entity){
        return Post.builder().id(entity.getId()).user_id(entity.getUser_id()).date(entity.getDate()).
                description(entity.getDescription()).build();
    }

    public static PostEntity PostConverter (Post post){
        return PostEntity.builder().date(post.getDate()).user_id(post.getUser_id()).
                description(post.getDescription()).build();
    }
}
