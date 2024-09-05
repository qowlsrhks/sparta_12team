package com.api.domain.comment.dto;

import com.api.domain.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private final Long boardId;
    private final Long commentId;
    private final Long userId;
    private final String username;
    private final String contents;
    private final LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment) {
        this.boardId = comment.getBoard().getId();
        this.contents = comment.getContents();
        this.username = comment.getUsername();
        this.commentId = comment.getId();
        this.userId = comment.getUserId();
        this.modifiedAt = comment.getModifiedAt();
    }
}
