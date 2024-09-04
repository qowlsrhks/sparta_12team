package com.api.domain.users.service;

import com.api.domain.users.dto.*;
import com.api.domain.users.entity.DeleteUser;
import com.api.domain.users.entity.User;
import com.api.domain.users.repository.DeleteUserRepository;
import com.api.domain.users.repository.UserRepository;
import com.api.domain.users.util.UserUtil;
import com.api.exceptions.DeactivatedUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    /*
        알림 / SSE / 이벤트 핸들러
        캐싱 - 중복조회
     */
    private final UserRepository userRepository;
    private final DeleteUserRepository deleteUserRepository;
    private final UserUtil userUtil;

    // 모든 회원 조회
    public List<UserResponseDto> getUsers() {
        // 활성 회원만 조회
        List<User> users = userRepository.findAllByisMemberIs(true);

        List<UserResponseDto> userLists = new ArrayList<>();
        for(User userList : users) {
            userLists.add(new UserResponseDto(userList));
        }

        return userLists;
    }

    // 선택한 회원 조회
    public UserResponseDto getUser(Long userId){
        User user = userUtil.findByUserId(userId);

        if(!user.isMember()){
            throw new DeactivatedUserException("이미 탈퇴한 회원입니다.");
        }

        return new UserResponseDto(user);
    }

    // 회원 정보 수정(이름, 소개)
    @Transactional
    public UserResponseDto updateUser(UserModifyRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(() ->
                new NullPointerException("회원이 존재하지 않습니다."));

        user.update(requestDto.getUsername(), requestDto.getIntroduce(), user.getPassword());

        return new UserResponseDto(user);
    }

    // 비밀번호 변경
    @Transactional
    public void updateUser (Long userId, UserPasswordRequestDto requestDto) {
        User user = userUtil.findByUserId(userId);

        String password = user.getPassword();
        String checkPassword = requestDto.getPassword();
        String newPassword = requestDto.getNewPassword();

        // 입력받은 비밀번호 검증
        if(!password.equals(checkPassword)){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 비밀번호 형식 검증
        if(!userUtil.PasswordPattern(newPassword)){
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
        User user = userUtil.findByUserId(requestDto.getUserId());

        if(!user.isMember()){
            throw new DeactivatedUserException("이미 탈퇴한 회원입니다.");
        }
        // 비밀번호 일치 여부 확인
        if(!user.getPassword().equals(requestDto.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 멤버 비활성화
        user.withdrawUser();

        // redis에 비활성 유저 저장
        DeleteUser deleteUser = new DeleteUser(user.getEmail(), LocalDateTime.now());
        deleteUserRepository.save(deleteUser);

        return new UserStatusResponseDto(user);
    }
}
