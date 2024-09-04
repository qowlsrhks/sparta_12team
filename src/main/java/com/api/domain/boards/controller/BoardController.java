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
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

//    게시물 생성
    @PostMapping("/{memberId}")
    public ResponseEntity<Board> create(@PathVariable Long memberId, @RequestBody BoardDto boardDto) {
        return ResponseEntity.ok( boardService.create(memberId,boardDto));
    }

//    뉴스피드 목록
    @GetMapping("/{memberId}/friendBoardList")
    public ResponseEntity<Page<Board>> getFriendBoardList(@PathVariable Long memberId,@RequestParam int page) {
        return ResponseEntity.ok(boardService.getFriendBoardList(memberId, page));
    }

//    뉴스피드 조회
    @GetMapping("/{memberId}/{boardId}friendBoard")
    public ResponseEntity<Page<Board>> getFriendBoard(@PathVariable Long memberId, @PathVariable Long boardId, @RequestParam int page) {
        return ResponseEntity.ok(boardService.getFriendBoard(memberId, page));
    }


//    특정 유저가 생성한 게시물 다건 조회
    @GetMapping("/{memberId}/userBoardList")
    public ResponseEntity<List<Board>> userBoardList(@PathVariable Long memberId) {
        return ResponseEntity.ok(boardService.findBoardsByUserId(memberId));
    }

//    단건 조회
    @GetMapping("/{memberId}/{boardId}/userBoard")
    public ResponseEntity<Board> findById(@PathVariable Long memberId, @PathVariable Long boardId) {
        return ResponseEntity.ok(boardService.findByUserIdAndBoardId(memberId, boardId));
    }

//    모든 게시물 조회
    @GetMapping("/boardAllList")
    public ResponseEntity<List<Board>> findAll() {
        return ResponseEntity.ok(boardService.findAll());
    }


//    게시물 수정
    @PutMapping("/{memberId}/{boardId}")
    public ResponseEntity<Board> update(@PathVariable Long memberId, @PathVariable Long boardId, @RequestBody BoardDto boardDto) {
        Board board = boardService.update(memberId, boardId, boardDto);
        return ResponseEntity.ok(board);
    }

//    게시물 삭제
    @DeleteMapping("/{memberId}/{boardId}")
    public ResponseEntity<Board> delete(@PathVariable Long memberId , @PathVariable Long boardId) {
        return ResponseEntity.ok(boardService.delete(memberId, boardId));
    }
}
