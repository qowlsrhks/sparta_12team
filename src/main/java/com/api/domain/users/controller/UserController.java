package com.api.domain.users.controller;

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
}