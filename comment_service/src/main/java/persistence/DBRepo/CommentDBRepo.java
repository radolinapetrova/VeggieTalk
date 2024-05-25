package persistence.DBRepo;

import model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import persistence.model.CommentEntity;

import java.util.List;

@Repository
public interface CommentDBRepo extends JpaRepository<CommentEntity, Long> {

    public List<CommentEntity> findAllByPostId(Long postId);

    public List<CommentEntity> findAllByPostIdAndRating(Long postId, Rating rating);

    public List<CommentEntity> findAllByRating(Rating rating);
}
