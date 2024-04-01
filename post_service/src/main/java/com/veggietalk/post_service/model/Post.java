package com.veggietalk.post_service.model;

import lombok.*;

import java.util.Objects;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private Long id;
    private String date;
    private Long userId;
    private String description;

}
