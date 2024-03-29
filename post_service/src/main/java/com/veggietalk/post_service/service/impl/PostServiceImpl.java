package com.veggietalk.post_service.service.impl;

import com.veggietalk.post_service.model.Post;
import com.veggietalk.post_service.persistence.PostRepo;
import com.veggietalk.post_service.persistence.model.PostEntity;
import com.veggietalk.post_service.service.PostService;
import com.veggietalk.post_service.service.converters.PostConverters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepo postRepo;

    LocalDate currentDate = LocalDate.now();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public Post createPost(Post post) throws IllegalArgumentException{
        if (post.getDescription() == null || post.getDescription().isEmpty()){
            throw new IllegalArgumentException("There must be a description given for the post!");
        }
        post.setDate(currentDate.format(formatter));
        return PostConverters.PostEntityConverter(postRepo.save(PostConverters.PostConverter(post)));
    }

    @Override
    public void deletePost(Long id, Long userId) throws IllegalArgumentException{
        Optional<PostEntity> post = postRepo.findById(id);
        if(post.isPresent()){
            postRepo.deletePost(id);
        }
        else{
            throw new IllegalArgumentException("The selected post does not exist :(");
        }
    }

    @Override
    public List<Post> getAllPosts() throws NoSuchElementException {
        List<PostEntity> posts = postRepo.getAllPosts();
        if(!posts.isEmpty()){
            return posts.stream().map(PostConverters::PostEntityConverter).toList();
        }
        throw new NoSuchElementException("There are no available posts");
    }




}
