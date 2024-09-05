package com.api.domain.friend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendResponseDto {
    private Long id;
    private String username;

    public FriendResponseDto() {
    }

    public FriendResponseDto(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}