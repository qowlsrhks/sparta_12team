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
    @PostMapping("/boards/{userId}")
    public ResponseEntity<BoardDto> createBoard(@PathVariable Long userId, @RequestBody BoardDto boardDto) {
        return ResponseEntity.ok( boardService.createBoard(userId,boardDto));
    }

//    뉴스피드 목록
    @GetMapping("/boards/{userId}/friendBoard")
    public ResponseEntity<Page<List<Board>>> getFriendsBoards(@PathVariable Long userId, @RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(boardService.getFriendBoardList(userId,page));
    }


//    유저 게시물 목록
    @GetMapping("/boards/{userId}/userBoardList")
    public ResponseEntity<Page<Board>> userBoardList(@PathVariable Long userId, @RequestParam(defaultValue = "0")int page) {
        return ResponseEntity.ok(boardService.userBoardList(userId, page));
    }

//    유저 게시물 조회
    @GetMapping("/boards/{boardId}/userBoard")
    public ResponseEntity<Board> userBoard(@PathVariable Long boardId) {
        return ResponseEntity.ok(boardService.userBoard(boardId));
    }

//    모든 게시물 목록
    @GetMapping("/boards/boardAllList")
    public ResponseEntity<Page<Board>> boardAllList(@RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(boardService.boardAllList(page));
    }


//    게시물 수정
    @PutMapping("/boards/{userId}/{boardId}")
    public ResponseEntity<Board> updateBoard(@PathVariable Long userId, @PathVariable Long boardId, @RequestBody BoardDto boardDto) {
        Board board = boardService.updateBoard(userId, boardId, boardDto);
        return ResponseEntity.ok(board);
    }

//    게시물 삭제
    @DeleteMapping("/boards/{userId}/{boardId}")
    public ResponseEntity<Board> deleteBoard(@PathVariable Long userId ,@PathVariable Long boardId) {
        return ResponseEntity.ok(boardService.deleteBoard(userId, boardId));
    }
}
