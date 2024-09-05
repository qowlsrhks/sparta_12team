package com.api.domain.friend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    private Long id;
    private String username;

    public UserRequestDto() {
    }

    public UserRequestDto(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}