package com.veggietalk.post_service.persistence.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Data
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
