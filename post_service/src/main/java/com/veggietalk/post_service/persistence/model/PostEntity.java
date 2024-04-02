package com.veggietalk.post_service.persistence.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Getter
@Setter
@Builder
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
