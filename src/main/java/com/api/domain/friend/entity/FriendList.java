package com.api.domain.friend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "friend_list")
@IdClass(FriendListId.class)
@Getter
@Setter
@NoArgsConstructor
public class FriendList {
    @Id
    private Long userId;

    @Id
    private Long friendId;

    public FriendList(Long userId, Long friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }
}