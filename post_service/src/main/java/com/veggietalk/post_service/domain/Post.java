package com.veggietalk.post_service.domain;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@Data
public class Post {
    private Long id;
    private String date;
    private Long user_id;
    private String description;
}
