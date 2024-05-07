package com.veggietalk.account_service.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private Long id;
    private String email;
    private String bio;
    private String userId;

    private List<Account> followers;
    private List<Account> following;

    public void addFollower(Account account){
        if (followers == null){
            followers = new ArrayList<>();
        }
        followers.add(account);
    }

    public void addFollowing(Account account){
        if (following == null){
            following = new ArrayList<>();
        }
        following.add(account);
    }

}
