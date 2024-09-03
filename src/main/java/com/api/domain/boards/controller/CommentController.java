package com.api.domain.boards.controller;

import com.api.domain.boards.dto.comment.CommentResponseDto;
import com.api.domain.boards.dto.comment.CommentSaveRequestDto;
import com.api.domain.boards.dto.comment.CommentSaveResponseDto;
import com.api.domain.boards.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    // 댓글저장
    @PostMapping("/boards/{boardId}/comments")
    public CommentSaveResponseDto saveResponseDto(
            @PathVariable Long boardId,
            @RequestBody CommentSaveRequestDto commentSaveRequestDto) {

        return commentService.saveComment(boardId, commentSaveRequestDto);
    }

    // 댓글 목록 조회
    @GetMapping("/boards/comments")
    public List<CommentResponseDto> getComments() {
        return commentService.getComments();
    }


}
