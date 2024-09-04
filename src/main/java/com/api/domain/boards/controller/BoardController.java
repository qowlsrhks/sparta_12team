package com.api.domain.boards.controller;

import com.api.domain.boards.dto.BoardDto;
import com.api.domain.boards.entity.Board;
import com.api.domain.boards.entity.Member;
import com.api.domain.boards.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

//    뉴스피드 목록
    @GetMapping("/boards/{memberId}/friendBoardList")
    public ResponseEntity<Page<Board>> getFriendBoardList(@PathVariable Long memberId,@RequestParam int page) {
        boardService.getFriendBoardList(memberId, page);
        return ResponseEntity.ok(boardService.getFriendBoardList(memberId, page));
    }

//    뉴스피드 조회
    @GetMapping("/boards/{memberId}/{boardId}friendBoard")
    public ResponseEntity<Page<Board>> getFriendBoard(@PathVariable Long memberId, @PathVariable Long boardId, @RequestParam int page) {
        boardService.getFriendBoard(memberId, page);
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
        Board board = boardService.userBoard(memberId, boardId);
        return ResponseEntity.ok(board);
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
    public Board deleteBoard(@PathVariable Long memberId ,@PathVariable Long boardId) {
        return boardService.deleteBoard(memberId, boardId);
    }
}
