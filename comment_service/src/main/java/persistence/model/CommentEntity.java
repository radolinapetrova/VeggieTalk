package persistence.model;

import jakarta.persistence.*;
import lombok.*;
import model.Rating;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String text;
    private Rating rating;
    private Long postId;
}
