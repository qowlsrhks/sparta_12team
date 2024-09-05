package com.api.domain.boards.dto.comment;

import lombok.Getter;

@Getter
public class CommentSaveResponseDto {

    private final Long boardId;
    private final String contents;
    private final String username;

    public CommentSaveResponseDto(Long boardId, String contents, String username) {
        this.boardId = boardId;
        this.contents = contents;
        this.username = username;
    }
}
