package controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import model.Rating;

@Getter
@Builder
@AllArgsConstructor
public class CommentResponse {
    private Long id;
    private String text;
    private Long userId;
    private Long postId;
    private Rating rating;
}
