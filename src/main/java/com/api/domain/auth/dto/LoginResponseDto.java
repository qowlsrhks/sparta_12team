package com.api.domain.auth.dto;

import com.api.domain.users.entity.User;
import lombok.Getter;

@Getter
public class LoginResponseDto {
    private final Long userId;
    private final String username;
    private final String email;
    private final String message = "로그인 성공";

    public LoginResponseDto(User user) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
