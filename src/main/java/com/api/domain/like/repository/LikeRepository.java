package com.api.domain.like.repository;

import com.api.domain.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUserEmailAndBoardId(String currentEmail, Long boardId);
}
