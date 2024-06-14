package com.veggietalk.post_service.controller.DTO;

import com.veggietalk.post_service.model.Category;
import com.veggietalk.post_service.model.DifficultyLevel;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PostRequest {
    private UUID accountId;
    private String description;
    private List<String> ingredients;
    private Category category;
    private DifficultyLevel level;
}
