package com.veggietalk.post_service.service.impl;

import com.veggietalk.post_service.model.Post;
import com.veggietalk.post_service.persistence.PostRepo;
import com.veggietalk.post_service.persistence.model.PostEntity;
import com.veggietalk.post_service.service.PostService;
import com.veggietalk.post_service.persistence.converters.PostConverters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepo postRepo;

    LocalDate currentDate = LocalDate.now();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public Post createPost(Post post) throws IllegalArgumentException{
        if (post.getDescription() == null || post.getDescription().isEmpty()){
            throw new IllegalArgumentException("There must be a description given for the post!");
        }
        post.setDate(currentDate.format(formatter));
        return postRepo.save(post);
    }

    public void deletePost(Long id, Long userId) throws IllegalArgumentException{
        postRepo.findById(id);
        postRepo.deletePost(id);
    }

    public List<Post> getAllPosts() throws NoSuchElementException {
        List<Post> posts = postRepo.getAllPosts();
        if(!posts.isEmpty()){
            return posts;
        }
        throw new NoSuchElementException("There are no available posts");
    }




}
