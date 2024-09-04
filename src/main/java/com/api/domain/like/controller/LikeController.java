package com.api.domain.like.controller;

import com.api.domain.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/boards/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping
    public void liked(@RequestParam Long boardId) {
        likeService.liked(boardId);
    }

    @DeleteMapping
    public void cancelLike(@RequestParam Long boardId) {
        likeService.cancelLike(boardId);
    }
}
