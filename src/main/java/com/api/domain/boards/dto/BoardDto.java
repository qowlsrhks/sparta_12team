package com.api.domain.boards.dto;

import com.api.domain.boards.entity.Member;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardDto {

    private Long boardId;

    private String contents;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private Member memberId;


    public BoardDto(Long boardId, String contents, LocalDateTime createdDate, Member memberId) {
        this.boardId = boardId;
        this.contents = contents;
        this.createdDate = createdDate;
        this.memberId = memberId;
    }
}
