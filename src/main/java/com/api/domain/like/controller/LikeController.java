package com.api.domain.like.controller;

import com.api.domain.like.service.LikeService;
import com.api.domain.users.dto.UserResponseDto;
import com.api.domain.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;
    private final UserService userService;

    @PostMapping
    public void liked(@RequestParam Long boardId) {
        likeService.liked(boardId);
    }

    @DeleteMapping
    public void cancelLike(@RequestParam Long boardId) {
        likeService.cancelLike(boardId);
    }

    @GetMapping("/who-liked")
    public ResponseEntity<List<UserResponseDto>> whoLiked(@RequestParam  Long boardId) {
        return ResponseEntity.ok(likeService.whoLiked(boardId));
    }
}
