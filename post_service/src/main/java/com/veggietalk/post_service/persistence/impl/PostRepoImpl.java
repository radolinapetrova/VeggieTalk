package com.veggietalk.post_service.persistence.impl;

import com.veggietalk.post_service.model.Post;
import com.veggietalk.post_service.persistence.DBRepos.PostDBRepo;
import com.veggietalk.post_service.persistence.PostRepo;
import com.veggietalk.post_service.persistence.model.PostEntity;
import com.veggietalk.post_service.service.converters.PostConverters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepoImpl implements PostRepo {

    private final PostDBRepo postDBRepo;

    @Override
    public PostEntity save(PostEntity post) {
        return postDBRepo.save(post);
    }

    @Override
    public List<PostEntity> getAllPosts() {
        return postDBRepo.findAll();
    }

    @Override
    public void deletePost(Long id) {
        postDBRepo.deleteById(id);
    }

    @Override
    public Optional<PostEntity> findById(Long id){
       return  postDBRepo.findById(id);
    }

}
