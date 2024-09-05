package com.api.domain.comment.dto;

import com.api.domain.auth.service.AuthService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentSaveRequestDto {
    private String contents;
}
