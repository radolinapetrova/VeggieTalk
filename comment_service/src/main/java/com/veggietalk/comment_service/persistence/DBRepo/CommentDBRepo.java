package com.veggietalk.comment_service.persistence.DBRepo;

import com.veggietalk.comment_service.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.veggietalk.comment_service.persistence.model.CommentEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentDBRepo extends JpaRepository<CommentEntity, UUID> {

    public List<CommentEntity> findAllByPostId(UUID postId);

    public List<CommentEntity> findAllByPostIdAndRating(UUID postId, Rating rating);

    public List<CommentEntity> findAllByRating(Rating rating);
}
