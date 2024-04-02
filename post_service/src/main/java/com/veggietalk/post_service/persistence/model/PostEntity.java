package com.veggietalk.post_service.persistence.model;


import com.veggietalk.post_service.model.Post;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.List;
import java.util.Objects;

@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "post")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String date;
    private Long user_id;
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private List<MediaFileEntity> media_files;

}
