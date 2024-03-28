package com.veggietalk.post_service.service.impl;

import com.veggietalk.post_service.domain.Post;
import com.veggietalk.post_service.persistence.PostRepo;
import com.veggietalk.post_service.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepo postRepo;

    LocalDate currentDate = LocalDate.now();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public Post createPost(Post post) {
        post.setDate(currentDate.format(formatter));
        return postRepo.save(post);
    }
}
