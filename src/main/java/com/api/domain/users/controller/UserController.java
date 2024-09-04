package com.api.domain.users.controller;

import com.api.domain.auth.service.AuthService;
import com.api.domain.users.dto.*;
import com.api.domain.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    // 모든 회원 조회
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getUsers(){
        return ResponseEntity.ok(userService.getUsers());
    }

    // 선택한 회원 조회
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getUser(userId));
    }

    // 회원 정보 수정(회원이름, 소개)
    @PutMapping
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody UserModifyRequestDto requestDto){
        return ResponseEntity.ok(userService.updateUser(requestDto));
    }

    // 비밀번호 변경
    @PutMapping("/{userId}")
    public void updateUser(@PathVariable Long userId, @RequestBody UserPasswordRequestDto requestDto){
        userService.updateUser(userId, requestDto);
    }

    // 회원 탈퇴 -> isMember = false;
    @PatchMapping
    public ResponseEntity<UserStatusResponseDto> withdrawUser(@RequestBody UserWithdrawRequestDto requestDto){
        return ResponseEntity.ok(userService.updateUser(requestDto));
    }

    @GetMapping("/whoAmI")
    public ResponseEntity<String> WhoAmI() {
        return ResponseEntity.ok(authService.currentUser().getEmail());
    }
}