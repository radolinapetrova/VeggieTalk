package persistence;

import model.Comment;
import model.Rating;

import java.util.List;
import java.util.Optional;

public interface CommentRepo {
    List<Comment> getPostComments(Long postId) throws IllegalArgumentException;
    List<Comment> getPostCommentsByRating(Long postId,Rating rating) throws IllegalArgumentException;
    List<Comment> getCommentsByRating(Rating rating) throws IllegalArgumentException;

    void deleteComment (Long commentId);

    Comment createComment(Comment comment);

    Comment findById(Long id) throws IllegalArgumentException;

}
