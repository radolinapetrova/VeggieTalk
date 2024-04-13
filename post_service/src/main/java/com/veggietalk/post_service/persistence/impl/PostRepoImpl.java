package com.veggietalk.post_service.persistence.impl;

import com.veggietalk.post_service.model.Post;
import com.veggietalk.post_service.persistence.DBRepos.PostDBRepo;
import com.veggietalk.post_service.persistence.PostRepo;
import com.veggietalk.post_service.persistence.converters.PostConverters;
import com.veggietalk.post_service.persistence.model.PostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepoImpl implements PostRepo {

    private final PostDBRepo postDBRepo;

    @Override
    public Post save(Post post) {
        return PostConverters.PostEntityConverter(postDBRepo.save(PostConverters.PostConverter(post)));
    }

    @Override
    public List<Post> getAllPosts() {
        return postDBRepo.findAll().stream().map(PostConverters::PostEntityConverter).toList();
    }

    @Override
    public void deletePost(Long id) {
        postDBRepo.deleteById(id);
    }

    @Override
    public Post findById(Long id) throws IllegalArgumentException{
        Optional<PostEntity> entity = postDBRepo.findById(id);
        if(entity.isPresent()){
            return PostConverters.PostEntityConverter(entity.get());
        }
       else{
           throw new IllegalArgumentException("The selected post does not exist :(");
        }
    }

}
