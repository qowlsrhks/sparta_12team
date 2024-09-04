package com.api.domain.auth.service;

import com.api.domain.auth.dto.LoginRequestDto;
import com.api.domain.users.dto.UserCreateRequestDto;
import com.api.domain.users.dto.UserResponseDto;
import com.api.domain.users.entity.User;
import com.api.domain.users.repository.UserRedisRepository;
import com.api.domain.users.repository.UserRepository;
import com.api.domain.users.service.EmailService;
import com.api.domain.users.util.UserUtil;
import com.api.exceptions.DeactivatedUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserUtil userUtil;
    private final EmailService emailService;
    private final UserRedisRepository userRedisRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원 가입
    public UserResponseDto createUser(UserCreateRequestDto requestDto) {
        // 이메일 형식 검증
        if(!userUtil.checkEmailPattern(requestDto.getEmail())) {
            throw new IllegalArgumentException("이메일 형식이 아닙니다.");
        }

        // 중복된 이메일 검증
        if(userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("중복된 이메일입니다.");
        }

        // 비밀번호 형식 검증
        if(!userUtil.PasswordPattern(requestDto.getPassword())) {
            throw new IllegalArgumentException("영어 대소문자, 숫자, 특수문자가 모두 포함되어야합니다.");
        }

        String userEmail = requestDto.getEmail();

        // 메일 인증 중인 email 인지 확인
        if(!userUtil.checkAuthEmail(userEmail)){ // 인증번호 발송
            emailService.sendEmail(userEmail);
            return null;
        }

        // 인증번호 확인
        if(userUtil.checkEmail(userEmail).getAuthNum() != (requestDto.getAuthNumber())){
            throw new IllegalArgumentException("인증번호가 일치하지 않습니다.");
        }

        User user = new User(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        User savedUser = userRepository.save(user);

        // redis에서 제거
        userRedisRepository.delete(userUtil.checkEmail(userEmail));

        return new UserResponseDto(savedUser);
    }

    //로그인
    public String login(LoginRequestDto requestDto) {
        User requestedUser = userRepository.findByEmail(requestDto.getEmail()).orElseThrow();
        if(!requestedUser.isMember()) {
            throw new DeactivatedUserException("로그인 할 수 없는 사용자입니다.");
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "로그인 성공";
    }
}
