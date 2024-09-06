package com.api.domain.boards.dto;

import com.api.domain.boards.entity.Board;
import com.api.domain.boards.entity.BoardImage;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardResponseDto {
    private final Long boardId;
    private final String username;
    private final String contents;
    private final Long likesCount;
    private final LocalDateTime modifiedAt;
    private String imageUrl;

    public BoardResponseDto(Board board) {
        this.boardId = board.getId();
        this.username = board.getUser().getUsername();
        this.contents = board.getContents();
        this.likesCount = board.getLikesCount();
        this.modifiedAt = board.getModifiedAt();
        this.imageUrl = board.getBoardImage()
                .map(BoardImage::getImageUrl)
                .orElse("null");
    }
}
