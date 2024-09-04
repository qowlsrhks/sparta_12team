package com.api.domain.boards.repository;

import com.api.domain.boards.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

}
