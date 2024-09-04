package com.api.domain.users.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRequestDto {
    private String username;
    private String email;
    private String password;
    private String introduce;
}
