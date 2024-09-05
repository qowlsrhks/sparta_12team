package com.api.domain.friend.repository;

import com.api.domain.friend.entity.FriendList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendListRepository extends JpaRepository<FriendList, Long> {
    void deleteByUserIdAndFriendId(Long userId, Long friendId);

    List<FriendList> findByUserId(Long id);
}
