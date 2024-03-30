package com.veggietalk.post_service.persistence.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "media_file")
public class MediaFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String file_name;
    private String file_extension;

    @ManyToOne
    @JoinColumn(name="post_id")
    private PostEntity post;

}
