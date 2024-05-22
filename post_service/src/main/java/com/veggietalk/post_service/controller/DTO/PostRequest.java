package com.veggietalk.post_service.controller.DTO;

import com.veggietalk.post_service.model.Category;
import com.veggietalk.post_service.model.DifficultyLevel;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PostRequest {
    private Long userId;
    private String description;
    private List<String> ingredients;
    private Category category;
    private DifficultyLevel level;
}
