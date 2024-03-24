package com.veggietalk.post_service.persistence.impl;

import com.veggietalk.post_service.domain.Post;
import com.veggietalk.post_service.persistence.DBRepos.PostDBRepo;
import com.veggietalk.post_service.persistence.PostRepo;
import com.veggietalk.post_service.persistence.converters.PostConverters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostRepoImpl implements PostRepo {

    private final PostDBRepo postDBRepo;

    @Override
    public Post save(Post post) {
        return PostConverters.PostEntityConverter(postDBRepo.save(PostConverters.PostConverter(post)));
    }
}
