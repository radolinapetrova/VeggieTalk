package com.veggietalk.post_service.controller.DTO;

import com.veggietalk.post_service.model.Category;
import com.veggietalk.post_service.model.DifficultyLevel;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PostResponse {
    private UUID id;
    private UUID accountId;
    private String description;
    private String date;
    private DifficultyLevel level;
    private Category category;
    private List<String> ingredients;
    private List<UUID> fileIds;
}
