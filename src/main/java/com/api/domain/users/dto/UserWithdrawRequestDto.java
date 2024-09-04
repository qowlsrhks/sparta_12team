package com.api.domain.users.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserWithdrawRequestDto {
    private Long userId;
    private String password;
}
