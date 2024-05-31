package com.veggietalk.comment_service.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    public UUID id;
    private UUID accountId;
    private UUID postId;
    private String text;
    private Rating rating;
}
