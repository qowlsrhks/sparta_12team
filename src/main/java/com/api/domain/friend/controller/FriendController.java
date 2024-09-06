package com.api.domain.friend.controller;

import com.api.domain.auth.service.AuthService;
import com.api.domain.friend.dto.FriendRequestDto;
import com.api.domain.friend.dto.FriendResponseDto;
import com.api.domain.friend.service.FriendService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/friends")
public class FriendController {

    private final FriendService friendService;
    private final AuthService authService;

    public FriendController(FriendService friendService, AuthService authService) {
        this.friendService = friendService;
        this.authService = authService;
    }

    // 테스트 용도
    @PostMapping("/add")
    public ResponseEntity<String> addFriend(@RequestBody FriendRequestDto requestDto) {
        friendService.addFriend(requestDto);
        return ResponseEntity.ok("친구 추가 완료되었습니다.");
    }

    @PostMapping("/request")
    public ResponseEntity<String> requestFriend(@RequestParam Long friendId) {
        friendService.requestFriend(friendId);
        return ResponseEntity.ok("친구 추가 요청했습니다.");
    }

    @PostMapping("/accept")
    public ResponseEntity<String> acceptFriendRequest(@RequestParam Long friendId) {
        friendService.acceptFriendRequest(friendId);
        return ResponseEntity.ok("친구요청을 허락하셨습니다.");
    }

    @PostMapping("/refuse")
    public ResponseEntity<String> refuseFriendRequest(@RequestParam Long friendId) {
        friendService.refuseFriendRequest(friendId);
        return ResponseEntity.ok("친구요청을 거절하셨습니다.");
    }

    @PostMapping("/remove")
    public ResponseEntity<String> removeFriend(@RequestParam Long friendId) {
        friendService.removeFriend(friendId);
        return ResponseEntity.ok("친구 삭제가 완료되었습니다.");
    }

    @GetMapping
    public ResponseEntity<List<FriendResponseDto>> getFriends(@RequestParam Long userId) {
        return ResponseEntity.ok(friendService.getFriends(userId));
    }
}
