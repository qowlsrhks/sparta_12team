package com.api.domain.friend.service;

import com.api.domain.friend.entity.FriendList;
import com.api.domain.friend.repository.FriendListRepository;
import com.api.domain.friend.dto.UserResponseDto;
import com.api.domain.users.entity.User;
import com.api.domain.users.repository.UserRepository;
import com.api.domain.users.util.UserUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class FriendService {

    private final UserRepository userRepository;
    private final FriendListRepository friendListRepository;
    private final UserUtil userUtil;

    public FriendService(UserRepository userRepository, FriendListRepository friendListRepository, UserUtil userUtil) {
        this.userRepository = userRepository;
        this.friendListRepository = friendListRepository;
        this.userUtil = userUtil;
    }

    public List<UserResponseDto> getFriends(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found by userId"));

        return user.getFriends().stream()
                .map(friend -> new UserResponseDto(friend.getId(), friend.getUsername()))
                .collect(Collectors.toList());
    }

    public void addFriend(Long userId, Long friendId, String username, String friendUsername) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found by userId"));
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new IllegalArgumentException("Friend not found by friendId"));

        if (!user.getUsername().equals(username) || !friend.getUsername().equals(friendUsername)) {
            throw new IllegalArgumentException("Username or friendUsername does not match provided userId or friendId");
        }

        if (user.getFriends().contains(friend)) {
            throw new IllegalArgumentException("이미 친구목록에 추가된 유저입니다.");
        }

        user.getFriends().add(friend);
        friend.getFriends().add(user);
        userRepository.save(user);
        userRepository.save(friend);
    }

    public void requestFriend(Long userId, Long friendId, String username, String friendUsername) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("요청자의 ID로 유저를 찾을 수 없습니다."));
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new IllegalArgumentException("친구의 ID로 유저를 찾을 수 없습니다."));

        if (!user.getUsername().equals(username)) {
            throw new IllegalArgumentException("요청자의 ID와 Username이 매칭되지 않았습니다.");
        }

        if (!friend.getUsername().equals(friendUsername)) {
            throw new IllegalArgumentException("친구의 ID와 Username이 매칭되지 않았습니다.");
        }

        if (userId.equals(friendId)) {
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
    public void acceptFriendRequest(Long userId, Long friendId, String username, String friendUsername) {
        if (username.equals(friendUsername)) {
            throw new IllegalArgumentException("자신은 요청해놓고 자신이 친구 추가 허가할 수 없습니다.");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        User friend = userRepository.findByUsername(friendUsername)
                .orElseThrow(() -> new IllegalArgumentException("Friend not found"));

        if (!user.getFriendRequests().contains(friend)) {
            throw new IllegalArgumentException("이 사용자의 친구 요청이 없습니다.");
        }

        user.getFriendRequests().remove(friend);
        user.getFriends().add(friend);
        friend.getFriends().add(user);

        userRepository.save(user);
        userRepository.save(friend);

        friendListRepository.save(new FriendList(userId, friendId));
        friendListRepository.save(new FriendList(friendId, userId));
    }

    @Transactional
    public void removeFriend(Long userId, Long friendId, String username, String friendUsername) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found by userId"));
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new IllegalArgumentException("Friend not found by friendId"));

        if (!user.getUsername().equals(username) || !friend.getUsername().equals(friendUsername)) {
            throw new IllegalArgumentException("Username or friendUsername does not match provided userId or friendId");
        }

        if (!user.getFriends().contains(friend) || !friend.getFriends().contains(user)) {
            throw new IllegalArgumentException("User and friend are not friends.");
        }

        user.getFriends().remove(friend);
        friend.getFriends().remove(user);

        userRepository.save(user);
        userRepository.save(friend);

        friendListRepository.deleteByUserIdAndFriendId(userId, friendId);
        friendListRepository.deleteByUserIdAndFriendId(friendId, userId);
    }
}
