package com.api.domain.auth.service;

import com.api.domain.auth.dto.LoginRequestDto;
import com.api.domain.auth.dto.LoginResponseDto;
import com.api.domain.users.dto.UserCreateRequestDto;
import com.api.domain.users.dto.UserResponseDto;
import com.api.domain.users.entity.User;
import com.api.domain.users.repository.UserRedisRepository;
import com.api.domain.users.repository.UserRepository;
import com.api.domain.users.service.EmailService;
import com.api.domain.users.util.UserUtil;
import com.api.exceptions.DeactivatedUserException;
import com.api.exceptions.VerifyEmailSendedException;
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
//        //입력 Body 유효성 검사
//        validateRequest(requestDto);
//        //이메일 인증
//        verifyEmail(requestDto);

        User user = new User(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        User savedUser = userRepository.save(user);

//        // redis에서 제거
//        userRedisRepository.delete(userUtil.checkEmail(requestDto.getEmail()));

        return new UserResponseDto(savedUser);
    }

    public void validateRequest(UserCreateRequestDto requestDto) {
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
    }

    public void verifyEmail(UserCreateRequestDto requestDto) {
        String userEmail = requestDto.getEmail();

        // 메일 인증 중인 email 인지 확인
        if(!userUtil.checkAuthEmail(userEmail)){ // 인증번호 발송
            emailService.sendEmail(userEmail);
            throw new VerifyEmailSendedException("메일이 전송되었습니다. 인증번호와 함께 다시 요청을 보내주십시오.");
        }

        // 인증번호 확인
        if(userUtil.checkEmail(userEmail).getAuthNum() != (requestDto.getAuthNumber())){
            throw new IllegalArgumentException("인증번호가 일치하지 않습니다.");
        }
    }


    //로그인
    public LoginResponseDto login(LoginRequestDto requestDto) {
        User requestedUser = userRepository.findByEmail(requestDto.getEmail()).orElseThrow();
        if(!requestedUser.isMember()) {
            throw new DeactivatedUserException("로그인 할 수 없는 사용자입니다.");
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new LoginResponseDto(requestedUser);
    }

    //요청한 사용자가 작성자인지 판단
    public boolean isUserOwner(User user) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        String ownersEmail = user.getEmail();
        return currentUserEmail.equals(ownersEmail);
    }

    public User currentUser() {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(currentUserEmail).orElseThrow(
                () -> new NullPointerException("현재 로그인한 이메일로 사용자를 찾을 수 없습니다.")
        );
    }
}
