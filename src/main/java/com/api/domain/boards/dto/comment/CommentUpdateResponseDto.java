package com.api.domain.boards.dto.comment;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentUpdateResponseDto {

    private final Long id;
    private final String contents;


    public CommentUpdateResponseDto(Long id, String contents) {
        this.id = id;
        this.contents = contents;
    }
}
