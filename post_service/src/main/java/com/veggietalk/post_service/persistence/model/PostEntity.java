package com.veggietalk.post_service.persistence.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
public class PostEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    private String date;

    @Column(name = "account_id", columnDefinition = "UUID")
    private UUID accountId;

    private String description;

    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    private RecipeEntity recipe;

    @ElementCollection(targetClass = UUID.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "post_files", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "file_id", nullable = false)
    private List<UUID> fileId;

}
