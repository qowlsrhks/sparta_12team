package com.api.domain.boards.dto;

import com.api.domain.boards.entity.Board;
import lombok.Getter;


@Getter
public class BoardDto{

    private Long boardId;
    private String contents;
    private String imageurls;

    public BoardDto(Board board) {
        this.boardId = board.getBoardId();
        this.contents = board.getContents();
        this.imageurls = board.getBoardImages().toString();
    }

    public void changeImageUrl(String imageurls){
        this.imageurls = imageurls;
    }
}
