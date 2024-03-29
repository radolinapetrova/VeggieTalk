package com.veggietalk.post_service.service;

import com.veggietalk.post_service.model.Post;

import java.util.List;

public interface PostService {
    Post createPost(Post post) throws IllegalArgumentException;

    void deletePost(Long id, Long userId) throws IllegalArgumentException;

    List<Post> getAllPosts();
}
