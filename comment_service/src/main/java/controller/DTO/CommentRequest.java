package controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import model.Rating;

@Getter
@AllArgsConstructor
public class CommentRequest {
    private String text;
    private Long userId;
    private Long postId;
    private Rating rating;
}
