package com.api.domain.boards.repository;

import com.api.domain.boards.entity.Board;
import com.api.domain.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

//    Page<Board> findByUserId(User userId, Pageable pageable);

    Optional<Board> findByUserAndId(User userId, Long boardId);

    List<Board> findBoardsByUser(User user);

    Optional<Board> findByUserIdAndId(Long userId, Long boardId);

    List<Board> findByUserId(Long userId);
}

