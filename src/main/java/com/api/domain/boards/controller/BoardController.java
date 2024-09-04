package com.api.domain.boards.controller;

import com.api.domain.boards.dto.BoardDto;
import com.api.domain.boards.entity.Board;
import com.api.domain.boards.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
        return ResponseEntity.ok( boardService.createBoard(memberId,boardDto));
    }

//    뉴스피드 목록
    @GetMapping("/boards/{memberId}/friendBoardList")
    public ResponseEntity<Page<Board>> getFriendBoardList(@PathVariable Long memberId,@RequestParam int page) {
        return ResponseEntity.ok(boardService.getFriendBoardList(memberId, page));
    }

//    뉴스피드 조회
    @GetMapping("/boards/{memberId}/{boardId}friendBoard")
    public ResponseEntity<Page<Board>> getFriendBoard(@PathVariable Long memberId, @PathVariable Long boardId, @RequestParam int page) {
        return ResponseEntity.ok(boardService.getFriendBoard(memberId, page));
    }


//    유저 게시물 목록
    @GetMapping("/boards/{memberId}/userBoardList")
    public ResponseEntity<List<Board>> userBoardList(@PathVariable Long memberId) {
        return ResponseEntity.ok(boardService.userBoardList(memberId));
    }

//    유저 게시물 조회
    @GetMapping("/boards/{memberId}/{boardId}/userBoard")
    public ResponseEntity<Board> userBoard(@PathVariable Long memberId,@PathVariable Long boardId) {
        return ResponseEntity.ok(boardService.userBoard(memberId, boardId));
    }

//    모든 게시물 목록
    @GetMapping("/boards/boardAllList")
    public ResponseEntity<List<Board>> boardAllList() {
        return ResponseEntity.ok(boardService.boardAllList());
    }


//    게시물 수정
    @PutMapping("/boards/{memberId}/{boardId}")
    public ResponseEntity<Board> updateBoard(@PathVariable Long memberId, @PathVariable Long boardId, @RequestBody BoardDto boardDto) {
        Board board = boardService.updateBoard(memberId, boardId, boardDto);
        return ResponseEntity.ok(board);
    }

//    게시물 삭제
    @DeleteMapping("/boards/{memberId}/{boardId}")
    public ResponseEntity<Board> deleteBoard(@PathVariable Long memberId ,@PathVariable Long boardId) {
        return ResponseEntity.ok(boardService.deleteBoard(memberId, boardId));
    }
}
