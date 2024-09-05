package com.api.domain.friend.repository;

import com.api.domain.friend.entity.FriendRequest;
import com.api.domain.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    boolean existsBySenderAndReceiver(User sender, User receiver);
}
