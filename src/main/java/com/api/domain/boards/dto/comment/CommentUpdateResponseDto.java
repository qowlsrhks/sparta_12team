package com.api.domain.boards.dto.comment;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentUpdateResponseDto {

    private final Long boardId;
    private final String contents;


    public CommentUpdateResponseDto(Long boardId, String contents) {
        this.boardId = boardId;
        this.contents = contents;
    }
}
