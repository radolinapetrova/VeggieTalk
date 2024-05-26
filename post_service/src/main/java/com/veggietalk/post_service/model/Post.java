package com.veggietalk.post_service.model;

import lombok.*;

import java.util.UUID;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private UUID id;
    private String date;
    private UUID userId;
    private String description;
    private Recipe recipe;
}
