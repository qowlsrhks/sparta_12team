package com.api.domain.boards.dto.comment;

import lombok.Getter;

@Getter
public class CommentResponseDto {

    private final Long boardId;
    private final String contents;

    public CommentResponseDto(Long boardId, String contents) {
        this.boardId = boardId;
        this.contents = contents;
    }
}
