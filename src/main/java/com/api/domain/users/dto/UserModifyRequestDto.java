package com.api.domain.users.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserModifyRequestDto {
    private String username;
    private String introduce;
}
