package com.veggietalk.post_service.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private UUID id;
    private String date;
    private UUID accountId;
    private String description;
    private Recipe recipe;
    private List<UUID> filesIds;

    public void addFile(UUID id){
        if (this.filesIds == null){
            this.filesIds = new ArrayList<>();
        }
        this.filesIds.add(id);
    }
}
