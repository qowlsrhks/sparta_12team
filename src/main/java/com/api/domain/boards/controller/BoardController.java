package com.api.domain.boards.controller;

import com.api.domain.boards.dto.BoardDto;
import com.api.domain.boards.entity.Board;
import com.api.domain.boards.entity.Member;
import com.api.domain.boards.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;

//    게시물 생성
    @PostMapping("/boards/{memberId}")
    public ResponseEntity<Board> createBoard(@PathVariable Long memberId, @RequestBody BoardDto boardDto) {
        Board board = boardService.createBoard(memberId,boardDto);
        return ResponseEntity.ok(board);
    }

//    유저 게시물 조회
    @GetMapping("/boards/{memberId}")
    public List<Board> userBoard(@PathVariable Long memberId) {
        return boardService.userBoard(memberId);

    }

//    모든 게시물 조회
    @GetMapping("/boards/{boardId}/boardList")
    public List<BoardDto> getBoardList(@PathVariable Long boardId) {
        return boardService.boardList(boardId);
    }

//    게시물 수정
    @PutMapping("/boards/{boardId}")
    public ResponseEntity<Board> updateBoard(@PathVariable Long boardId, @RequestBody BoardDto boardDto) {
        Board board = boardService.updateBoard(boardId,boardDto);
        return ResponseEntity.ok(board);
    }

//    게시물 삭제
    @DeleteMapping("/boards/{boardId}")
    public void deleteBoard(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
    }
}
