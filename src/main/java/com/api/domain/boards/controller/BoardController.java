package com.api.domain.boards.controller;

import com.api.domain.boards.dto.BoardDto;
import com.api.domain.boards.entity.Board;
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
    @GetMapping("/boards/{memberId}/{boardId}/userBoard")
    public ResponseEntity<Board> userBoard(@PathVariable Long memberId,@PathVariable Long boardId) {
        Board board = boardService.userBoard(memberId, boardId);
        return ResponseEntity.ok(board);
    }

//    모든 게시물 조회
    @GetMapping("/boards/boardList")
    public List<BoardDto> getBoardList(@RequestBody BoardDto boardDto) {
        return boardService.boardAllList(boardDto);
    }


//    게시물 수정
    @PutMapping("/boards/{memberId}/{boardId}")
    public ResponseEntity<Board> updateBoard(@PathVariable Long memberId, @PathVariable Long boardId, @RequestBody BoardDto boardDto) {
        Board board = boardService.updateBoard(memberId, boardId, boardDto);
        return ResponseEntity.ok(board);
    }

//    게시물 삭제
    @DeleteMapping("/boards/{memberId}/{boardId}")
    public Board deleteBoard(@PathVariable Long memberId ,@PathVariable Long boardId) {
        return boardService.deleteBoard(memberId, boardId);
    }
}
