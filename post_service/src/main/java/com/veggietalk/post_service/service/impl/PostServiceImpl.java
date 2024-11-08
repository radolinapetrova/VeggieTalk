package com.veggietalk.post_service.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.veggietalk.post_service.model.Category;
import com.veggietalk.post_service.model.DifficultyLevel;
import com.veggietalk.post_service.model.Post;
import com.veggietalk.post_service.persistence.PostRepo;
import com.veggietalk.post_service.rabbitmq_config.Producer;
import com.veggietalk.post_service.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PostServiceImpl implements PostService {

    private final PostRepo postRepo;

    @Autowired
    private Producer producer;

    @Autowired
    private AmazonS3 amazonS3;


    private String bucketName = "veggietalkbucket";

    LocalDate currentDate = LocalDate.now();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Post createPost(Post post) throws IllegalArgumentException{
        if (post.getDescription() == null || post.getDescription().isEmpty()){
            throw new IllegalArgumentException("There must be a description given for the post!");
        }
        post.setDate(currentDate.format(formatter));
        UUID uuid = UUID.randomUUID();
        post.addFile(uuid);
        return postRepo.save(post);
    }


    @Override
    public Post uploadPostWithFiles(Post post, MultipartFile file) throws IOException {

        if (post.getDescription() == null || post.getDescription().isEmpty()){
            throw new IllegalArgumentException("There must be a description given for the post!");
        }
        post.setDate(currentDate.format(formatter));
        UUID uuid = UUID.randomUUID();
        post.addFile(uuid);
        ObjectMetadata metadata = new ObjectMetadata();
        String contentType = file.getContentType();
        metadata.setContentType(contentType);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, uuid.toString(), file.getInputStream(), metadata);
        amazonS3.putObject(putObjectRequest);
        return postRepo.save(post);
    }

    @Override
    public List<Post> findByAccountId(UUID account) {
        return postRepo.findByAccountId(account);
    }


    public void deletePost(UUID id, UUID accountId, String role) throws IllegalArgumentException{
        Post post = postRepo.findById(id);
        if (!Objects.equals(post.getAccountId(), accountId) && !Objects.equals(role, "ADMIN")){
            throw new IllegalArgumentException("You do not have the right to delete this post");
        }
        postRepo.deletePost(id);
        producer.deletePost(id.toString());
    }

    public List<Post> getAllPosts(int pageNumber) throws NoSuchElementException {
        List<Post> posts = postRepo.getAllPosts(pageNumber);
        if(!posts.isEmpty()){
            return posts;
        }
        throw new NoSuchElementException("There are no available posts");
    }


    @Override
    public List<Post> findAllRecipesByDifficulty(DifficultyLevel level) {
        return postRepo.findAllRecipesByDifficulty(level);
    }

    @Override
    public List<Post> findAllRecipesByCategory(Category category) {
        return postRepo.findAllRecipesByCategory(category);
    }

    @Override
    public List<Post> findAllRecipesByIngredients(List<String> ingredients) {
        return postRepo.findAllByIngredientsContaining(ingredients);
    }

    @Override
    public List<Post> findAllRecipes(){
        return postRepo.findAllRecipes();
    }

    @Override
    public void deleteByAccountId(UUID accountId) throws IllegalArgumentException{

        List<Post> posts = new ArrayList<>();

        try{
            posts = this.findByAccountId(accountId);
        }
        catch (IllegalArgumentException e){
            log.info("No posts found");
        }


        if (!posts.isEmpty()){
            List<UUID> allFileIds = posts.stream()
                    .flatMap(post -> post.getFilesIds().stream())
                    .toList();

            for (UUID fileId : allFileIds){
                DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName, fileId.toString());
                amazonS3.deleteObject(deleteObjectRequest);
            }

            List<UUID> postIds = postRepo.deleteByAccountId(accountId);
            for (UUID postID: postIds){
                producer.deletePost(postID.toString());
            }
        }


    }


}
