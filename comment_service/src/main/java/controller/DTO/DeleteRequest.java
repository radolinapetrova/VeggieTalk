package controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteRequest {
    private Long commentId;
    private String role;
    private Long userId;
}
