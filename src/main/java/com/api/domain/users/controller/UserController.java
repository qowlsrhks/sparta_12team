package com.api.domain.users.controller;

import com.api.domain.auth.service.AuthService;
import com.api.domain.users.dto.*;
import com.api.domain.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
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
    public ResponseEntity<List<UserResponseDto>> findAll(){
        return ResponseEntity.ok(userService.getUsers());
    }

    // 선택한 회원 조회
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long userId){
        return ResponseEntity.ok(userService.findById(userId));
    }

    //내 프로필 보기
    @GetMapping("/my-profile")
    public ResponseEntity<MyProfileResponseDto> myProfile() {
        return ResponseEntity.ok(userService.myProfile());
    }

    // 회원 정보 수정(회원이름, 소개)
    @PutMapping
    public ResponseEntity<UserResponseDto> update(@RequestBody UserModifyRequestDto requestDto){
        return ResponseEntity.ok(userService.update(requestDto));
    }

    // 비밀번호 변경
    @PutMapping("/{userId}")
    public void update(@PathVariable Long userId, @RequestBody UserPasswordRequestDto requestDto){
        userService.update(userId, requestDto);
    }

    // 회원 탈퇴 -> isMember = false;
    @PatchMapping
    public ResponseEntity<UserStatusResponseDto> withdraw(@RequestBody UserWithdrawRequestDto requestDto){
        return ResponseEntity.ok(userService.update(requestDto));
    }

    //현재 인증된 유저 확인
    @GetMapping("/whoAmI")
    public ResponseEntity<String> WhoAmI() {
        return ResponseEntity.ok(authService.currentUser().getEmail());
    }
}