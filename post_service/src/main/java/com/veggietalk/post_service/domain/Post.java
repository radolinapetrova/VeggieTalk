package com.veggietalk.post_service.domain;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private Long id;
    private String date;
    private Long user_id;
    private String description;
}
