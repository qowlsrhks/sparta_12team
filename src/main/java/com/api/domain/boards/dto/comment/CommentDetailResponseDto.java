package com.api.domain.boards.dto.comment;

import lombok.Getter;

@Getter
public class CommentDetailResponseDto {

    private final Long id;
    private final String contents;
    private final String username;

    public CommentDetailResponseDto(Long id, String contents, String username) {
        this.id = id;
        this.contents = contents;
        this.username = username;
    }
}
