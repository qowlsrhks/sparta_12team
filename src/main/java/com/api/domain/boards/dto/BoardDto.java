package com.api.domain.boards.dto;

import com.api.domain.boards.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class BoardDto{

    private Long boardId;

    private String contents;

    public BoardDto(Board board) {
        this.boardId = board.getBoardId();
        this.contents = board.getContents();
    }

}
