package com.api.domain.boards.dto;

import com.api.domain.boards.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDto {
    private final Long boardId;
    private final String username;
    private final String contents;
    private final LocalDateTime modifiedAt;

    public BoardResponseDto(Board board) {
        this.boardId = board.getBoardId();
        this.username = board.getUser().getUsername();
        this.contents = board.getContents();
        this.modifiedAt = board.getModifiedAt();
    }
}
