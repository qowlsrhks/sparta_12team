package com.api.domain.friend.controller;

import com.api.domain.friend.dto.FriendRequestDto;
import com.api.domain.friend.dto.FriendResponseDto;
import com.api.domain.friend.service.FriendService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class FriendController {

    private final FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    // 테스트 용도
    @PostMapping("/add-friend")
    public ResponseEntity<String> addFriend(@RequestBody FriendRequestDto requestDto) {
        friendService.addFriend(requestDto);
        return ResponseEntity.ok("친구 추가 완료되었습니다.");
    }

    @PostMapping("/request-friend")
    public ResponseEntity<String> requestFriend(@RequestBody FriendRequestDto requestDto) {
        friendService.requestFriend(requestDto);
        return ResponseEntity.ok("친구 추가 요청했습니다.");
    }

    @PostMapping("/accept-friend-request")
    public ResponseEntity<String> acceptFriendRequest(@RequestBody FriendRequestDto requestDto) {
        friendService.acceptFriendRequest(requestDto);
        return ResponseEntity.ok("친구요청을 허락하셨습니다.");
    }

    @PostMapping("/refuse-friend-request")
    public ResponseEntity<String> refuseFriendRequest(@RequestBody FriendRequestDto requestDto) {
        friendService.refuseFriendRequest(requestDto);
        return ResponseEntity.ok("친구요청을 거절하셨습니다.");
    }

    @PostMapping("/remove-friend")
    public ResponseEntity<String> removeFriend(@RequestBody FriendRequestDto requestDto) {
        friendService.removeFriend(requestDto);
        return ResponseEntity.ok("친구 삭제가 완료되었습니다.");
    }

    @GetMapping("/friends")
    public ResponseEntity<List<FriendResponseDto>> getFriends(@RequestParam Long userId) {
        return ResponseEntity.ok(friendService.getFriends(userId));
    }
}
