package service;

import lombok.AllArgsConstructor;
import model.Comment;
import model.Rating;
import org.springframework.stereotype.Service;
import persistence.CommentRepo;
import service.impl.CommentService;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepo commentRepo;

    @Override
    public List<Comment> getPostComments(Long postId) throws IllegalArgumentException {
        return commentRepo.getPostComments(postId);
    }

    @Override
    public List<Comment> getPostCommentsByRating(Long postId, Rating rating, Long userId) throws IllegalAccessException, IllegalArgumentException {
        if (userId == 0){
            throw new IllegalAccessException("Create an account if you want to filter the comments");
        }

        return commentRepo.getPostCommentsByRating(postId, rating);
    }

    @Override
    public List<Comment> getCommentsByRating(Rating rating, Long userId) throws IllegalAccessException, IllegalArgumentException {
        if(Objects.equals(userId, 0L)){
            throw new IllegalAccessException("Create an account if you want to filter the comments");
        }

        return commentRepo.getCommentsByRating(rating);
    }

    @Override
    public void deleteComment(Long commentId, Long userId, String role) throws IllegalAccessException{
        Comment comment = commentRepo.findById(commentId);

        if(Objects.equals(comment.getUserId(), userId) || Objects.equals(role, "ADMIN")){
            commentRepo.deleteComment(commentId);
        }
        else throw new IllegalAccessException("You are not authorized to delete this post");
    }

    @Override
    public Comment createComment(Comment comment) throws IllegalAccessException{
        if(Objects.equals(comment.getUserId(), 0L)){
            throw new IllegalAccessException("You are not authorized to create a post");
        }
        return commentRepo.createComment(comment);
    }
}
