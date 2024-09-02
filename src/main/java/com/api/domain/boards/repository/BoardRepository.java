package com.api.domain.boards.repository;

import com.api.domain.boards.entity.Board;
import com.api.domain.boards.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByBoardId(Long boardId);


    List<Board> findByMemberId(Member member);

}
