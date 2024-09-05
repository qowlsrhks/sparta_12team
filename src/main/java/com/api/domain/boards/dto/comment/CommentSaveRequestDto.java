package com.api.domain.boards.dto.comment;

import lombok.Getter;

@Getter
public class CommentSaveRequestDto {

    private String contents;
    private String username;

}
