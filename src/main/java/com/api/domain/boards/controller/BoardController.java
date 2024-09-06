package com.api.domain.boards.controller;

import com.api.domain.boards.dto.BoardRequestDto;
import com.api.domain.boards.dto.BoardResponseDto;
import com.api.domain.boards.entity.Board;
import com.api.domain.boards.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

//    게시물 생성
    @PostMapping
    public ResponseEntity<BoardResponseDto> create(@RequestParam("contents") String contents, @RequestParam(value = "file",required = false) MultipartFile file) {
        return ResponseEntity.ok( boardService.create(contents, file));
    }


//    뉴스피드 조회
    @GetMapping("/newsfeed")
    public ResponseEntity<Page<BoardResponseDto>> getFriendBoard(Integer pageNum) {
        return ResponseEntity.ok(boardService.newsfeed(pageNum));
    }


//    특정 유저가 작성한 게시물 다건 조회
    @GetMapping("/{memberId}")
    public ResponseEntity<Page<BoardResponseDto>> findByUserId(@PathVariable Long memberId) {
        return ResponseEntity.ok(boardService.findByUserId(memberId));
    }

//    특정 유저가 작성한 게시물 단건 조회
    @GetMapping("/{memberId}/{boardId}")
    public ResponseEntity<Board> findByUserIdAndBoardId(@PathVariable Long memberId, @PathVariable Long boardId) {
        return ResponseEntity.ok(boardService.findByUserIdAndBoardId(memberId, boardId));
    }

//    모든 게시물 조회
    @GetMapping
    public ResponseEntity<List<BoardResponseDto>> findAll() {
        return ResponseEntity.ok(boardService.findAll());
    }

//    게시물 수정
    @PutMapping("/{boardId}")
    public ResponseEntity<Board> update(@PathVariable Long boardId, @RequestBody BoardRequestDto boardRequestDto) {
        Board board = boardService.update(boardId, boardRequestDto);
        return ResponseEntity.ok(board);
    }

//    게시물 삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity<String> delete(@PathVariable Long boardId) {
        return ResponseEntity.ok(boardService.delete(boardId));
    }
}
