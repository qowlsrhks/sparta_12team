package com.api.domain.friend.service;

import com.api.domain.auth.service.AuthService;
import com.api.domain.friend.dto.FriendRequestDto;
import com.api.domain.friend.dto.FriendResponseDto;
import com.api.domain.friend.entity.FriendList;
import com.api.domain.friend.repository.FriendListRepository;
import com.api.domain.users.entity.User;
import com.api.domain.users.repository.UserRepository;
import com.api.domain.users.util.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class FriendService {

    private final UserRepository userRepository;
    private final FriendListRepository friendListRepository;
    private final UserUtil userUtil;
    private final AuthService authService;

    public List<FriendResponseDto> getFriends(Long userId) {
        User user = userUtil.findByUserId(userId);

        return user.getFriends().stream()
                .map(friend -> new FriendResponseDto(friend.getId(), friend.getUsername()))
                .collect(Collectors.toList());
    }

    public void addFriend(FriendRequestDto requestDto) {
        User user = userUtil.findByUserId(requestDto.getUserId());
        User friend = userUtil.findByUserId(requestDto.getFriendId());

        if (user.getFriends().contains(friend)) {
            throw new IllegalArgumentException("이미 친구목록에 추가된 유저입니다.");
        }

        user.getFriends().add(friend);
        friend.getFriends().add(user);
        userRepository.save(user);
        userRepository.save(friend);
    }

    public void requestFriend(Long friendId) {
        User user = authService.currentUser();
        User friend = userUtil.findByUserId(friendId);

        if (user.getId().equals(friend.getId())) {
            throw new IllegalArgumentException("자기자신을 친구 요청할 수 없습니다.");
        }

        if (user.getFriendRequests().contains(friend)) {
            throw new IllegalArgumentException("이미 친구 요청을 하였습니다.");
        }

        if (user.getFriends().contains(friend)) {
            throw new IllegalArgumentException("이미 친구 목록에 추가된 유저입니다.");
        }

        user.getFriendRequests().add(friend);
        userRepository.save(user);
    }

    @Transactional
    public void acceptFriendRequest(Long friendId) {
        User user = userUtil.findByUserId(friendId);
        User friend = authService.currentUser();

        if (user.getId().equals(friend.getId())) {
            throw new IllegalArgumentException("유저 자신을 친구로 맺을 수 없습니다.");
        }

        if (!user.getFriendRequests().contains(friend)) {
            throw new IllegalArgumentException("이 사용자의 친구 요청이 없습니다.");
        }

        user.getFriendRequests().remove(friend);
        user.getFriends().add(friend);
        friend.getFriends().add(user);

        userRepository.save(user);
        userRepository.save(friend);

        friendListRepository.save(new FriendList(user.getId(), friend.getId()));
        friendListRepository.save(new FriendList(friend.getId(), user.getId()));
    }

    @Transactional
    public void refuseFriendRequest(Long friendId) {
        User user = userUtil.findByUserId(friendId);
        User friend = authService.currentUser();

        if (user.getId().equals(friend.getId())) {
            throw new IllegalArgumentException("유저 자신을 친구로 맺을 수 없습니다.");
        }

        if (!user.getFriendRequests().contains(friend)) {
            throw new IllegalArgumentException("이 사용자의 친구 요청이 없습니다.");
        }

        user.getFriendRequests().remove(friend);
        userRepository.save(user);

        friendListRepository.save(new FriendList(user.getId(), friend.getId()));
        friendListRepository.save(new FriendList(friend.getId(), user.getId()));
    }

    @Transactional
    public void removeFriend(Long friendId) {
        User user = authService.currentUser();
        User friend = userUtil.findByUserId(friendId);

        if (!user.getFriends().contains(friend) || !friend.getFriends().contains(user)) {
            throw new IllegalArgumentException("친구가 아닙니다.");
        }

        user.getFriends().remove(friend);
        friend.getFriends().remove(user);

        userRepository.save(user);
        userRepository.save(friend);

        friendListRepository.deleteByUserIdAndFriendId(user.getId(), friend.getId());
        friendListRepository.deleteByUserIdAndFriendId(friend.getId(), user.getId());
    }
}
