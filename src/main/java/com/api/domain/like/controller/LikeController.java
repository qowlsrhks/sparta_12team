package com.api.domain.like.controller;

import com.api.domain.boards.dto.BoardResponseDto;
import com.api.domain.like.service.LikeService;
import com.api.domain.users.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    //좋아요 추가
    @PostMapping
    public ResponseEntity<BoardResponseDto> liked(@RequestParam Long boardId) {
        return ResponseEntity.ok(likeService.liked(boardId));
    }

    //좋아요 삭제
    @DeleteMapping
    public ResponseEntity<BoardResponseDto> cancelLike(@RequestParam Long boardId) {
        return ResponseEntity.ok(likeService.cancelLike(boardId));
    }

    @GetMapping("/who-liked")
    public ResponseEntity<List<UserResponseDto>> whoLiked(@RequestParam  Long boardId) {
        return ResponseEntity.ok(likeService.whoLiked(boardId));
    }

    @GetMapping("/what-liked")
    public ResponseEntity<List<BoardResponseDto>> whatLiked(@RequestParam Long userId) {
        return ResponseEntity.ok(likeService.whatLiked(userId));
    }
}
