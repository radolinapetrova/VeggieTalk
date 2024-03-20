package com.veggietalk.post_service.persistence.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "post")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String date;
    private Long user_id;
    private String description;
}
