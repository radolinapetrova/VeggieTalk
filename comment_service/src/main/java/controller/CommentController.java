package controller;

import controller.DTO.CommentRequest;
import controller.DTO.CommentResponse;
import controller.DTO.DeleteRequest;
import controller.DTO.FilterCommentsRequest;
import controller.converters.RequestConverters;
import io.micrometer.core.util.internal.logging.InternalLogLevel;
import lombok.RequiredArgsConstructor;
import model.Comment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.impl.CommentService;

import java.util.List;

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
            service.deleteComment(request.getCommentId(), request.getUserId(), request.getRole());
            return ResponseEntity.ok().body("You have successfully deleted this account");
        }
        catch (IllegalAccessException e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getPostComments(@PathVariable(value = "id") Long id){

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
            return ResponseEntity.ok().body(service.getPostCommentsByRating(request.getPostId(), request.getRating(), request.getUserId()).stream().map(RequestConverters::CommentConverter).toList());
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
            return ResponseEntity.ok().body(service.getCommentsByRating(request.getRating(), request.getUserId()).stream().map(RequestConverters::CommentConverter));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(417).body(e.getMessage());
        }
        catch (IllegalAccessException e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }


}
