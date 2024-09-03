package com.api.domain.users.service;

import com.api.domain.config.JwtUtil;
import com.api.domain.users.dto.UserRequestDto;
import com.api.domain.users.dto.UserResponseDto;
import com.api.domain.users.entity.User;
import com.api.domain.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;



    // 회원 가입
    public UserResponseDto createUser(UserRequestDto requestDto) {
        // 이메일 형식 검증
        if(!userService.checkEmailPattern(requestDto.getEmail())) {
            throw new IllegalArgumentException("이메일 형식이 아닙니다.");
        }
        // 중복된 이메일 검증
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("중복된 이메일입니다.");
        }
        // 비밀번호 형식 검증
        if(!userService.PasswordPattern(requestDto.getPassword())) {
            throw new IllegalArgumentException("영어 대소문자, 숫자, 특수문자가 모두 포함되어야합니다.");
        }

        //유저 저장
        User user = new User(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        User savedUser = userRepository.save(user);

        return new UserResponseDto(savedUser);
    }
}
