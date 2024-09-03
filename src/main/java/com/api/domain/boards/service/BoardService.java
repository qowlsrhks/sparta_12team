package com.api.domain.boards.service;


import com.api.domain.boards.entity.Member;
import com.api.domain.boards.repository.BoardRepository;
import com.api.domain.boards.dto.BoardDto;
import com.api.domain.boards.entity.Board;
import com.api.domain.boards.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

//    게시글 생성
    @Transactional
    public Board createBoard(Long memberId, BoardDto boardDto) {
        Board board = new Board();
        board.setBoardId(boardDto.getBoardId());
        board.setContents(boardDto.getContents());
        board.setCreatedDate(LocalDateTime.now());
        board.setModifiedDate(LocalDateTime.now());
        board.setMemberId(memberRepository.findBymemberId(memberId));
        return boardRepository.save(board);
    }
//    유저 게시글 조회
    public List<Board> userBoard(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        return boardRepository.findByMemberId(member);
    }

//    게시글 조회
    public List<BoardDto> boardList(Long boardId) {
        List<Board> boardList = boardRepository.findByBoardId(boardId);
        List<BoardDto> boardDtoList = new ArrayList<>();
        for (Board board : boardList) {
            BoardDto boardDto = new BoardDto(
                    board.getBoardId(),
                    board.getContents(),
                    board.getCreatedDate(),
                    board.getMemberId()
            );
            boardDtoList.add(boardDto);
        }
        return boardDtoList;
    }

//    게시글 수정
    @Transactional
    public Board updateBoard(Long boardId, BoardDto boardDto) {
        Board board = new Board();
        board.setBoardId(boardId);
        board.setContents(boardDto.getContents());
        board.setModifiedDate(LocalDateTime.now());
        return boardRepository.save(board);
    }

//    게시글 삭제
    @Transactional
    public void deleteBoard(Long boardId) {
        boardRepository.deleteById(boardId);
    }
}
