package com.api.domain.friend.dto;

import lombok.Getter;

@Getter
public class FriendRequestDto {
    private Long userId;
    private Long friendId;

    public FriendRequestDto() {
    }

    public FriendRequestDto(Long id, Long friendId) {
        this.userId = id;
        this.friendId = friendId;
    }
}