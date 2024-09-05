package com.api.domain.boards.repository;

import com.api.domain.boards.entity.Board;
import com.api.domain.users.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {



    Optional<Board> findByUserAndBoardId(User userId, Long boardId);

    Page<Board> findAllByUser(User userId,Pageable pageable);

    @Query("SELECT b FROM Board b WHERE b.user.userId IN (SELECT f.friendUser.userId FROM Friend f WHERE f.user.userId = :userId)")
    Page<List<Board>> findBoardsByUserFriends(@Param("userId") Long userId, Pageable pageable);

    Page<Board> findAll(Pageable pageable);


}

