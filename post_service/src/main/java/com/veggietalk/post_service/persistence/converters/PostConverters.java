package com.veggietalk.post_service.persistence.converters;

import com.veggietalk.post_service.model.Post;
import com.veggietalk.post_service.model.Recipe;
import com.veggietalk.post_service.persistence.model.PostEntity;
import com.veggietalk.post_service.persistence.model.RecipeEntity;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class PostConverters {

    public static Post PostEntityConverter(PostEntity entity){

        Post post = Post.builder().id(entity.getId()).userId(entity.getUser_id()).date(entity.getDate()).
                description(entity.getDescription()).build();

        if (entity.getRecipe() != null){
            post.setRecipe(RecipeConverters.RecipeEntityConverter(entity.getRecipe()));
        }
        return post;
    }


    public static PostEntity PostConverter (Post post){
       return PostEntity.builder().date(post.getDate()).user_id(post.getUserId()).
                description(post.getDescription()).build();
    }
}
