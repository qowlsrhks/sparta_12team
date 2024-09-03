package com.api.domain.users.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserPasswordRequestDto {
    private String password;
    private String newPassword;
}
