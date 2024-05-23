package com.veggietalk.account_service.persistence.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String bio;
    private String user_id;
//
//    @ManyToMany(fetch = FetchType.LAZY)
//    @Cascade({org.hibernate.annotations.CascadeType.ALL})
//    @JoinTable(
//            name = "follower_relationship",
//            joinColumns = @JoinColumn(name = "following_id"),
//            inverseJoinColumns = @JoinColumn(name = "follower_id")
//    )
//    private List<AccountEntity> followers = new ArrayList<>();
//
//    @ManyToMany(mappedBy = "followers", fetch = FetchType.LAZY)
//    private List<AccountEntity> following = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "follower_relationship",
            joinColumns = @JoinColumn(name = "following_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    private List<AccountEntity> followers = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "follower_relationship",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    private List<AccountEntity> following = new ArrayList<>();

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "follower_relationship",
//            joinColumns = @JoinColumn(name = "following_id"),
//            inverseJoinColumns = @JoinColumn(name = "follower_id")
//    )
//    private List<AccountEntity> followers = new ArrayList<>();
//
//    @ManyToMany(mappedBy = "followers", fetch = FetchType.LAZY)
//    private List<AccountEntity> following = new ArrayList<>();

}
