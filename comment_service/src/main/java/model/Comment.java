package model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    public Long id;
    private Long userId;
    private Long postId;
    private String text;
    private Rating rating;
}
