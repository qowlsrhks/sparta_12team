package com.api.domain.users.service;

import com.api.domain.users.dto.*;
import com.api.domain.users.entity.User;
import com.api.domain.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 모든 회원 조회
    public List<UserResponseDto> getUsers() {
        List<User> users = userRepository.findAll();

        List<UserResponseDto> userLists = new ArrayList<>();
        for(User userList : users) {
            userLists.add(new UserResponseDto(userList));
        }

        return userLists;
    }

    // 선택한 회원 조회
    public UserResponseDto getUser(Long userId){
        User user = findByUserId(userId);

        return new UserResponseDto(user);
    }

    // 회원 정보 수정(이름, 소개)
    @Transactional
    public UserResponseDto updateUser(UserRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(() ->
                new NullPointerException("회원이 존재하지 않습니다."));

        user.update(requestDto.getUsername(), requestDto.getIntroduce(), user.getPassword());

        return new UserResponseDto(user);
    }

    // 비밀번호 변경
    @Transactional
    public void updateUser (Long userId, UserPasswordRequestDto requestDto) {
        User user = findByUserId(userId);

        String password = user.getPassword();
        String checkPassword = requestDto.getPassword();
        String newPassword = requestDto.getNewPassword();

        // 입력받은 비밀번호 검증
        if(!password.equals(checkPassword)){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 비밀번호 형식 검증
        if(!PasswordPattern(newPassword)){
            throw new IllegalArgumentException("영어 대소문자, 숫자, 특수문자가 모두 포함되어야합니다.");
        }

        // 현재 비밀번호와 변경할 비밀번호 일치 여부 확인
        if(password.equals(newPassword)){
            throw new IllegalArgumentException("현재 비밀번호와 동일합니다.");
        }

        user.update(user.getUsername(), user.getIntroduce(),newPassword);
    }

    // 회원 탈퇴(isMember : true -> false)
    @Transactional
    public UserStatusResponseDto updateUser(UserWithdrawRequestDto requestDto) {
        User user = findByUserId(requestDto.getUserId());

        // 비밀번호 일치 여부 확인
        if(!user.getPassword().equals(requestDto.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        user.withdrawUser();

        return new UserStatusResponseDto(user);
    }

    private User findByUserId(Long UserId){
        User user = userRepository.findByUserId(UserId).orElseThrow(() ->
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
}
