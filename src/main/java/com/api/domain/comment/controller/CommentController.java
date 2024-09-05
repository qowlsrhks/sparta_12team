package com.api.domain.comment.controller;

import com.api.domain.comment.dto.CommentSaveRequestDto;
import com.api.domain.comment.dto.CommentResponseDto;
import com.api.domain.comment.dto.CommentUpdateRequestDto;
import com.api.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("boards/{boardId}/comments")
    public ResponseEntity<CommentResponseDto> save(@PathVariable Long boardId, @RequestBody CommentSaveRequestDto commentSaveRequestDto) {
        return ResponseEntity.ok(commentService.save(boardId, commentSaveRequestDto));
    }

    // 전체 조회
    @GetMapping("boards/{boardId}/comments")
    public ResponseEntity<Page<CommentResponseDto>> findAllByBoardId(@PathVariable Long boardId, @RequestParam(required = false) Integer pageNum, Integer pageSize) {
        return ResponseEntity.ok(commentService.findAllByBoardId(boardId, pageNum, pageSize));
    }

    // 단건 조회
    @GetMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> findByCommentId(@PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.findByCommentId(commentId));
    }

    // 수정
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> update(@PathVariable Long commentId,
                                                     @RequestBody CommentUpdateRequestDto commentUpdateRequestDto) {
        return ResponseEntity.ok(commentService.update(commentId, commentUpdateRequestDto));
    }

    // 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> delete(@PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.delete(commentId));
    }


}
