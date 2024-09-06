package com.api.domain.users.service;

import com.api.domain.auth.service.AuthService;
import com.api.domain.users.dto.*;
import com.api.domain.users.entity.DeleteUser;
import com.api.domain.users.entity.User;
import com.api.domain.users.repository.DeleteUserRepository;
import com.api.domain.users.repository.UserRepository;
import com.api.domain.users.util.UserUtil;
import com.api.exceptions.DeactivatedUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    // 모든 회원 조회
    @Transactional
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
    @Transactional
    public UserResponseDto findById(Long userId){
        User user = userUtil.findByUserId(userId);

        if(!user.isMember()){
            throw new AccessDeniedException("이미 탈퇴한 회원입니다.");
        }

        return new UserResponseDto(user);
    }

    @Transactional(readOnly = true)
    public MyProfileResponseDto myProfile() {
        User me = authService.currentUser();

        return new MyProfileResponseDto(me);


    }

    // 회원 정보 수정(이름, 소개)
    @Transactional
    public UserResponseDto update(UserModifyRequestDto requestDto) {
        User user = userRepository.findByEmail(authService.currentUser().getEmail()).orElseThrow(() ->
                new NullPointerException("회원이 존재하지 않습니다."));

        if(!authService.isUserOwner(user)) {
            throw new IllegalArgumentException("본인만 수정할 수 있습니다.");
        }

        if(requestDto.getUsername() != null) {
            user.setUsername(requestDto.getUsername());
        }
        if(requestDto.getIntroduce() != null) {
            user.setIntroduce(requestDto.getIntroduce());
        }

        userRepository.save(user);

        return new UserResponseDto(user);
    }

    // 비밀번호 변경
    @Transactional
    public void updatePassword(UserPasswordRequestDto requestDto) {
        User user = authService.currentUser();

        if(!authService.isUserOwner(user)) {
            throw new IllegalArgumentException("본인만 수정할 수 있습니다.");
        }

        String currentPassword = requestDto.getPassword();
        String newPassword = requestDto.getNewPassword();

        // 입력받은 비밀번호 검증
        if(!passwordEncoder.matches(currentPassword, user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 비밀번호 형식 검증
        if(!userUtil.PasswordPattern(newPassword)){
            throw new IllegalArgumentException("영어 대소문자, 숫자, 특수문자가 모두 포함되어야합니다.");
        }

        // 현재 비밀번호와 변경할 비밀번호 일치 여부 확인
        if(passwordEncoder.matches(newPassword, user.getPassword())){
            throw new IllegalArgumentException("현재 비밀번호와 동일합니다.");
        }

        user.update(
                user.getUsername(),
                user.getIntroduce(),
                passwordEncoder.encode(newPassword));
    }

    // 회원 탈퇴(isMember : true -> false)
    @Transactional
    public UserStatusResponseDto update(UserWithdrawRequestDto requestDto) {
        User user = authService.currentUser();
        if(!authService.isUserOwner(user)) {
            throw new IllegalArgumentException("본인만 탈퇴할 수 있습니다.");
        }

        if(!user.isMember()){
            throw new DeactivatedUserException("이미 탈퇴한 회원입니다.");
        }
        // 비밀번호 일치 여부 확인
        if(!passwordEncoder.matches(requestDto.getPassword(),user.getPassword())){
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
