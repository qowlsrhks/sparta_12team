package com.api.domain.users.controller;

import com.api.domain.users.dto.UserRequestDto;
import com.api.domain.users.dto.UserResponseDto;
import com.api.domain.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    // 회원 가입
    @PostMapping("/users")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto requestDto){
        return ResponseEntity.ok(userService.createUser(requestDto));
    }

    // 모든 회원 조회
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> getUsers(){
        return ResponseEntity.ok(userService.getUsers());
    }

    // 선택한 회원 조회
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getUser(userId));
    }
}
