package com.veggietalk.account_service.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private UUID id;
    private String email;
    private String bio;
    private UUID userId;

    private List<Account> followers = new ArrayList<>();
    private List<Account> following = new ArrayList<>();

    public void addFollower(Account account) throws Exception {
        if (account != null) {
            if (followers == null) {
                followers = new ArrayList<>();
            }
            if (this.followers.stream().noneMatch(acc -> acc.getId().equals(account.getId()))) {
                this.followers.add(account);
            } else {
                throw new Exception("Already following this account");
            }
        } else {
           throw new Exception("Attempted to add a null follower.");
        }
    }

    public void addFollowing(Account account) throws Exception {
        if (account != null) {
            if (following == null) {
                following = new ArrayList<>();
            }
            if (this.following.stream().noneMatch(acc -> acc.getId().equals(account.getId()))) {
                this.following.add(account);
            } else {
               throw new Exception("Already following this account");
            }
        } else {
            throw new Exception("Attempted to add a null following.");
        }
    }

    public void removeFollower(Account account) throws Exception {
        if (account != null) {
            if (followers != null && followers.contains(account)) {
                followers.remove(account);
                throw new Exception("Follower removed: " + account.getEmail());
            }
        }
    }

    public void removeFollowing(Account account) throws Exception {
        if (account != null) {
            if (following != null && following.contains(account)) {
                following.remove(account);
                throw new Exception("Following removed: " + account.getEmail());
            }
        }
    }

}
