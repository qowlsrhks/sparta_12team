package com.api.domain.users.service;

import com.api.domain.users.dto.UserRequestDto;
import com.api.domain.users.dto.UserResponseDto;
import com.api.domain.users.entity.User;
import com.api.domain.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 회원 가입
    public UserResponseDto createUser(UserRequestDto requestDto) {
        User user = new User(requestDto);
        User savedUser = userRepository.save(user);

        return new UserResponseDto(savedUser);
    }

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
        User user = userRepository.findByUserId(userId).orElseThrow(() ->
                new IllegalArgumentException("회원이 존재하지 않습니다."));

        return new UserResponseDto(user);
    }
}
