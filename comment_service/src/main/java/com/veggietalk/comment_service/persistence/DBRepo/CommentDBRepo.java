package com.veggietalk.comment_service.persistence.DBRepo;

import com.veggietalk.comment_service.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.veggietalk.comment_service.persistence.model.CommentEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommentDBRepo extends JpaRepository<CommentEntity, UUID> {

    Optional<List<CommentEntity>> findAllByPostId(UUID postId);

    Optional<List<CommentEntity>> findAllByPostIdAndRating(UUID postId, Rating rating);

    Optional<List<CommentEntity>> findAllByRating(Rating rating);

    Optional<List<CommentEntity>> findAllByAccountId(UUID accountId);

}
