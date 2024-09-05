package com.api.domain.friend.controller;

import com.api.domain.friend.dto.UserResponseDto;
import com.api.domain.friend.service.FriendService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api")
public class FriendController {

    private final FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @PostMapping("/user/add-friend")
    @ResponseBody
    public ResponseEntity<String> addFriend(@RequestParam Long userId, @RequestParam Long friendId, @RequestParam String username, @RequestParam String friendUsername) {
        try {
            friendService.addFriend(userId, friendId, username, friendUsername);
            return ResponseEntity.ok("친구 추가 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/user/request-friend")
    @ResponseBody
    public ResponseEntity<String> requestFriend(@RequestParam Long userId, @RequestParam Long friendId, @RequestParam String username, @RequestParam String friendUsername) {
        try {
            friendService.requestFriend(userId, friendId, username, friendUsername);
            return ResponseEntity.ok("친구 추가 요청했습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/user/accept-friend-request")
    @ResponseBody
    public ResponseEntity<String> acceptFriendRequest(@RequestParam Long userId, @RequestParam Long friendId, @RequestParam String username, @RequestParam String friendUsername) {
        try {
            friendService.acceptFriendRequest(userId, friendId, username, friendUsername);
            return ResponseEntity.ok("친구 추가를 허락하셨습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/user/remove-friend")
    @ResponseBody
    public ResponseEntity<String> removeFriend(@RequestParam Long userId, @RequestParam Long friendId, @RequestParam String username, @RequestParam String friendUsername) {
        try {
            friendService.removeFriend(userId, friendId, username, friendUsername);
            return ResponseEntity.ok("친구 삭제가 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/friends")
    @ResponseBody
    public ResponseEntity<List<UserResponseDto>> getFriends(@RequestParam Long userId) {
        try {
            List<UserResponseDto> friends = friendService.getFriends(userId);
            return ResponseEntity.ok(friends);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
