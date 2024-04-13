package com.veggietalk.post_service.persistence.converters;

import com.veggietalk.post_service.model.Post;
import com.veggietalk.post_service.persistence.model.PostEntity;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@NoArgsConstructor
public class PostConverters {

    public static Post PostEntityConverter(PostEntity entity){
        return Post.builder().id(entity.getId()).userId(entity.getUser_id()).date(entity.getDate()).
                description(entity.getDescription()).build();
    }

    public static PostEntity PostConverter (Post post){
        return PostEntity.builder().date(post.getDate()).user_id(post.getUserId()).
                description(post.getDescription()).build();
    }
}
