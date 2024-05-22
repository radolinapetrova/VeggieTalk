package com.veggietalk.post_service.model;

import lombok.*;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private Long id;
    private String date;
    private Long userId;
    private String description;
    private Recipe recipe;
}
