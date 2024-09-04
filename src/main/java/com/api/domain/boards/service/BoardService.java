package com.api.domain.boards.service;


import com.api.domain.boards.entity.Friend;
import com.api.domain.boards.entity.Member;
import com.api.domain.boards.repository.BoardRepository;
import com.api.domain.boards.dto.BoardDto;
import com.api.domain.boards.entity.Board;
import com.api.domain.boards.repository.FriendRepository;
import com.api.domain.boards.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    //    게시글 생성
    @Transactional
    public Board createBoard(Long memberId, BoardDto boardDto) {
        Board board = new Board();
        board.setBoardId(boardDto.getBoardId());
        board.setContents(boardDto.getContents());
        board.setMemberId(memberRepository.findBymemberId(memberId));
        board.setCreateAt(LocalDateTime.now());
        return boardRepository.save(board);
    }

//    뉴스피드 목록
    public Page<Board> getFriendBoardList(Long memberId , int page) {
        Optional<Member> member = memberRepository.findById(memberId);
        if(member.isPresent()){
            Pageable pageable = PageRequest.of(page, 10);
            return boardRepository.findByMemberId(member.get(), pageable);
        }else{
            throw new RuntimeException("등록된 회원이 없습니다");
        }
    }

//    뉴스피드 조회
    public Page<Board> getFriendBoard(Long memberId, int page) {
        Optional<Member> member = memberRepository.findById(memberId);
        if(member.isPresent()){
            List<Member> friends = member.get().getFriendList()
                    .stream()
                    .map(Friend::getFriendMember)
                    .collect(Collector.toList());
            Pageable pageable = PageRequest.of(page, 10);
            return boardRepository.findByMemberIdIn(friends, pageable);
        }else {
            throw new RuntimeException("등록된 회원이 없습니다");
        }
    }

    //유저 게시글 목록
    public List<Board> userBoardList(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("등록된 회원을 찾을 수 없습니다"));
        return boardRepository.findAllByMemberId(member);
    }

    //    유저 게시글 조회
    public Board userBoard(Long memberId, Long boardId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("등록된 회원을 찾을 수 없습니다"));
        Board board = boardRepository.findByMemberIdAndBoardId(member, boardId)
                .orElseThrow(() -> new RuntimeException("등록된 게시판을 찾을 수 없습니다"));
        if(board.getMemberId().getMemberId().equals(memberId)) {
            boardRepository.findAll();
            return board;
        }else{
            throw new RuntimeException("You are not authorized to update this board");
        }

    }

    //    모든 게시글 목록
    public List<Board> boardAllList() {
        return boardRepository.findAll();
    }

    //    게시글 수정
    @Transactional
    public Board updateBoard(Long memberId, Long boardId, BoardDto boardDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("등록된 회원을 찾을 수 없습니다"));
        Board board = boardRepository.findByMemberIdAndBoardId(member, boardId)
                .orElseThrow(() -> new RuntimeException("등록된 게시판을 찾을 수 없습니다"));
        if(board.getMemberId().getMemberId().equals(memberId)) {
            board.setContents(boardDto.getContents());
            board.setCreateAt(LocalDateTime.now());
            return boardRepository.save(board);
        }else{
            throw new RuntimeException("You are not authorized to update this board");
        }
    }

    //    게시글 삭제
    @Transactional
    public Board deleteBoard(Long memberId,Long boardId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("등록된 회원을 찾을 수 없습니다"));
        Board board = boardRepository.findByMemberIdAndBoardId(member, boardId)
                .orElseThrow(() -> new RuntimeException("등록된 게시판을 찾을 수 없습니다"));
        if(board.getMemberId().getMemberId().equals(memberId)) {
            boardRepository.delete(board);
            return board;
        }else{
            throw new RuntimeException("You are not authorized to delete this board");
        }
    }


}
