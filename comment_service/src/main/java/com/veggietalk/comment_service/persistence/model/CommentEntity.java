package com.veggietalk.comment_service.persistence.model;

import jakarta.persistence.*;
import lombok.*;
import com.veggietalk.comment_service.model.Rating;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class CommentEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;
    private UUID userId;
    private String text;
    private Rating rating;
    private UUID postId;
}
