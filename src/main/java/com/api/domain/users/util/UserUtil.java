package com.api.domain.users.util;

import com.api.domain.users.entity.Email;
import com.api.domain.users.entity.User;
import com.api.domain.users.repository.UserRedisRepository;
import com.api.domain.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class UserUtil {

    private final UserRepository userRepository;
    private final UserRedisRepository userRedisRepository;

    public User findByUserId(Long UserId){
        User user = userRepository.findById(UserId).orElseThrow(() ->
                new NullPointerException("회원이 존재하지 않습니다."));

        return user;
    }

    public boolean PasswordPattern(String password){
        // 비밀번호 정규표현식
        // 대소문자 1개이상, 숫자 1개이상, 특수문자 1개이상, 8자리 이상 20자리 이하
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,20}$";

        return Pattern.matches(passwordPattern, password);
    }

    public boolean checkEmailPattern(String email){
        // 이메일 정규표현식
        // abc._.ab@abc.a.c or abc._.ab@abc.ac
        String emailPattern = "^[\\w.]+@\\w+\\.\\w+(\\.\\w+)?";

        return Pattern.matches(emailPattern, email);
    }

    public Email checkEmail(String email){
        return userRedisRepository.findById(email).orElseThrow(() ->
                new IllegalArgumentException("무엇인가 오류"));
    }

    public boolean checkAuthEmail(String email){
        return userRedisRepository.findById(email).isPresent();
    }
}
