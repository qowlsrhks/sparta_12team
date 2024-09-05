package com.api.domain.friend.repository;

import com.api.domain.friend.entity.FriendList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendListRepository extends JpaRepository<FriendList, Long> {
    void deleteByUserIdAndFriendId(Long userId, Long friendId);
}
