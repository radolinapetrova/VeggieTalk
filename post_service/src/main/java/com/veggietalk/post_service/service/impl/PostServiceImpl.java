package com.veggietalk.post_service.service.impl;

import com.veggietalk.post_service.domain.Post;
import com.veggietalk.post_service.persistence.PostRepo;
import com.veggietalk.post_service.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepo postRepo;

    @Override
    public Post createPost(Post post) {
        return postRepo.save(post);
    }
}
