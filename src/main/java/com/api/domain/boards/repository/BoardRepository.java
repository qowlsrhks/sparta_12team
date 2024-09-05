package com.api.domain.boards.repository;

import com.api.domain.boards.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    Optional<Board> findByUserIdAndId(Long userId, Long boardId);

    List<Board> findAllByUserId(Long userId);

    Page<Board> findAllToPageByUserId(Long userId, Pageable pageable);
}

