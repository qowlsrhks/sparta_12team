package com.api.domain.users.controller;

import com.api.domain.config.JwtUtil;
import com.api.domain.users.dto.UserRequestDto;
import com.api.domain.users.dto.UserResponseDto;
import com.api.domain.users.repository.UserRepository;
import com.api.domain.users.service.AuthService;
import com.api.domain.users.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<?> create(@RequestBody UserRequestDto requestDto, HttpServletResponse response) throws IOException {
        try {
            UserResponseDto responseDto = authService.createUser(requestDto);

            String token = jwtUtil.createToken(responseDto.getEmail());
            jwtUtil.addJwtToHeader(token, response);

            return ResponseEntity.ok(responseDto);

        } catch (IllegalArgumentException e) {
            // 예외 발생 시 에러 응답 생성
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Bad Request");
            errorResponse.put("message", e.getMessage());

            // 400 상태 코드와 함께 에러 메시지 반환
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
