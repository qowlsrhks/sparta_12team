package com.api.domain.boards.dto;

import lombok.Getter;

@Getter
public class BoardDto{

    private final Long boardId;

    private final String contents;

    public BoardDto(Long boardId, String contents) {
        this.boardId = boardId;
        this.contents = contents;
    }
}
