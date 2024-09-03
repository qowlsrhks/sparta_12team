package com.api.domain.boards.controller;

import com.api.domain.boards.dto.BoardDto;
import com.api.domain.boards.dto.comment.*;
import com.api.domain.boards.entity.Board;
import com.api.domain.boards.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

//    // 댓글저장 => 작성
//    @PostMapping("/boards/{boardId}/comments")
//    public CommentSaveResponseDto saveResponseDto(
//            @PathVariable Long boardId,
//            @RequestBody CommentSaveRequestDto commentSaveRequestDto) {
//
//        return commentService.saveComment(boardId, commentSaveRequestDto);
//    }
//
//    // 댓글 목록 조회
//    @GetMapping("/boards/comments")
//    public List<CommentResponseDto> getComments() {
//        return commentService.getComments();
//    }
//
//    // 댓글 수정
//    @PutMapping("/board/comments/{commentId}")
//    public ResponseEntity<CommentUpdateResponseDto> updateComment( // 서비스의 코드도 함께 넘기게끔, @ResponseEntity
//                                                                   @PathVariable Long commentId, @RequestBody CommentUpdateRequestDto commentUpdateRequestDto) {
//        return ResponseEntity.ok(commentService.updateComment(commentId, commentUpdateRequestDto)).getHeaders();
//    }

    @PostMapping("/boards/{boardId}/comments")
    public CommentSaveResponseDto saveComment(@PathVariable Long boardId, @RequestBody CommentSaveRequestDto commentSaveRequestDto) {
        return commentService.saveComment(boardId, commentSaveRequestDto);
    }

    // 전체 조회
    @GetMapping("/boards/{boardId}/comments")
    public List<CommentDetailResponseDto> getComments (@PathVariable Long boardId) {
        return commentService.getComments(boardId);
    }

    // 단건 조회
    @GetMapping("/boards/comments/{commentId}")
    public CommentDetailResponseDto getComment (@PathVariable Long commentId) {
        return commentService.getComment(commentId);
    }

    // 수정
    @PutMapping("/boards/comments/{commentId}")
    public CommentUpdateResponseDto updateComment(@PathVariable Long commentId,
                                                  @RequestBody CommentUpdateRequestDto commentUpdateRequestDto) {
        return commentService.updateComment(commentId, commentUpdateRequestDto);
    }

    // 삭제
    @DeleteMapping("/boards/comments/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }


}
