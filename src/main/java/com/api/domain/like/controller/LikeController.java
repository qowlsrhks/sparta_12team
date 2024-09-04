package com.api.domain.like.controller;

import com.api.domain.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/boards/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping
    public void liked(@RequestParam Long boardId) {
        likeService.liked(boardId);
    }
}
