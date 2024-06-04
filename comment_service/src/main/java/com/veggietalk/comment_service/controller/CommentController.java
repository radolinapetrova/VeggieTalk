package com.veggietalk.comment_service.controller;

import com.veggietalk.comment_service.controller.DTO.DeleteRequest;
import com.veggietalk.comment_service.controller.DTO.CommentRequest;
import com.veggietalk.comment_service.controller.DTO.FilterCommentsRequest;
import com.veggietalk.comment_service.controller.converters.RequestConverters;
import lombok.RequiredArgsConstructor;
import com.veggietalk.comment_service.model.Comment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.veggietalk.comment_service.service.CommentService;

import java.util.UUID;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CommentController {

    private final CommentService service;

    @PostMapping()
    public ResponseEntity<Object> createComment(@RequestBody CommentRequest request){

        Comment comment;
        try{
            comment =  service.createComment(RequestConverters.CommentRequestConverter(request));
        }
        catch (IllegalAccessException e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
        return ResponseEntity.ok().body(RequestConverters.CommentConverter(comment));
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteComment(@RequestBody DeleteRequest request){
        try{
            service.deleteComment(request.getCommentId(), request.getAccountId(), request.getRole());
            return ResponseEntity.ok().body("You have successfully deleted this account");
        }
        catch (IllegalAccessException e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getPostComments(@PathVariable(value = "id") UUID id){

        try{
            return ResponseEntity.ok().body(service.getPostComments(id).stream().map(RequestConverters::CommentConverter).toList());
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(417).body(e.getMessage());
        }
    }

    @GetMapping("post/comment/rating")
    public ResponseEntity<Object> getPostCommentsByRating(@RequestBody FilterCommentsRequest request){
        try{
            return ResponseEntity.ok().body(service.getPostCommentsByRating(request.getPostId(), request.getRating(), request.getAccountId()).stream().map(RequestConverters::CommentConverter).toList());
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(417).body(e.getMessage());
        }
        catch (IllegalAccessException e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @GetMapping("comment/rating")
    public ResponseEntity<Object> getCommentsByRating(@RequestBody FilterCommentsRequest request){
        try{
            return ResponseEntity.ok().body(service.getCommentsByRating(request.getRating(), request.getAccountId()).stream().map(RequestConverters::CommentConverter));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(417).body(e.getMessage());
        }
        catch (IllegalAccessException e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }


}
