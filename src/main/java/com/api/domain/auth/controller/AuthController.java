package com.api.domain.auth.controller;

import com.api.domain.config.JwtUtil;
import com.api.domain.users.dto.UserRequestDto;
import com.api.domain.users.dto.UserResponseDto;
import com.api.domain.users.repository.UserRepository;
import com.api.domain.auth.service.AuthService;
import com.api.domain.users.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final AuthService authService;

    // 회원 가입
    @PostMapping("/register")
    public ResponseEntity<?> create(@RequestBody UserRequestDto requestDto, HttpServletResponse response) {

        UserResponseDto responseDto = authService.createUser(requestDto);

        String token = jwtUtil.createToken(responseDto.getEmail());
        jwtUtil.addJwtToHeader(token, response);

        return ResponseEntity.ok(responseDto);
    }

    //로그인
}

