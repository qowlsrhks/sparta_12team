package com.api.domain.boards.repository;

import com.api.domain.boards.entity.Board;
import com.api.domain.boards.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByBoardId(Long boardId);

    Board findByMemberId(Member member);

    Optional<Board> findByMemberIdAndBoardId(Member memberId, Long boardId);
}
