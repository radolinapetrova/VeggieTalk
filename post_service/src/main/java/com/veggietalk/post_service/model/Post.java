package com.veggietalk.post_service.model;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private Long id;
    private String date;
    private Long userId;
    private String description;
}
