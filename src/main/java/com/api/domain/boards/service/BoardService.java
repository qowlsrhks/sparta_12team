package com.api.domain.boards.service;


import com.api.domain.boards.repository.BoardRepository;
import com.api.domain.boards.dto.BoardDto;
import com.api.domain.boards.entity.Board;
import com.api.domain.users.entity.User;
import com.api.domain.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    //게시글 생성
    @Transactional
    public Board create(Long userId, BoardDto boardDto) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new RuntimeException("회원이 존재하지 않습니다."));
        Board board = new Board(boardDto.getContents(), user);
        return boardRepository.save(board);
    }

//    뉴스피드 목록
    public Page<Board> getFriendBoardList(Long userId , int page) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            Pageable pageable = PageRequest.of(page, 10);
            return boardRepository.findByUserId(user.get(), pageable);
        }else{
            throw new RuntimeException("등록된 회원이 없습니다");
        }
    }

//    뉴스피드 조회
    public Page<Board> getFriendBoard(Long userId, int page) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            Pageable pageable = PageRequest.of(page, 10);
            return null;
        }else {
            throw new RuntimeException("등록된 회원이 없습니다");
        }
    }

    //특정 유저가 작성한 게시물 다건 조회
    @Transactional
    public List<Board> findBoardsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("등록된 회원을 찾을 수 없습니다"));

        return boardRepository.findAllByUserId(user);
    }

    //   특정 유저의 게시물 단건 조회
    @Transactional
    public Board findByUserIdAndBoardId(Long userId, Long boardId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("등록된 회원을 찾을 수 없습니다"));
        Board board = boardRepository.findByUserIdAndBoardId(user, boardId)
                .orElseThrow(() -> new RuntimeException("등록된 게시판을 찾을 수 없습니다"));
        if(board.getUserId().getUserId().equals(userId)) {
            boardRepository.findAll();
            return board;
        }else{
            throw new RuntimeException("You are not authorized to update this board");
        }

    }

    //    게시물 전체조회
    @Transactional
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    //    게시글 수정
    @Transactional
    public Board update(Long userId, Long boardId, BoardDto boardDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("등록된 회원을 찾을 수 없습니다"));
        Board board = boardRepository.findByUserIdAndBoardId(user, boardId)
                .orElseThrow(() -> new RuntimeException("등록된 게시판을 찾을 수 없습니다"));
        if(board.getUserId().getUserId().equals(userId)) {

            Board updateBoard = new Board(boardDto.getContents(), user);
            return boardRepository.save(updateBoard);
        }else{
            throw new RuntimeException("You are not authorized to update this board");
        }
    }

    //    게시글 삭제
    @Transactional
    public Board delete(Long userId, Long boardId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("등록된 회원을 찾을 수 없습니다"));
        Board board = boardRepository.findByUserIdAndBoardId(user, boardId)
                .orElseThrow(() -> new RuntimeException("등록된 게시판을 찾을 수 없습니다"));
        if(board.getUserId().getUserId().equals(userId)) {
            boardRepository.delete(board);
            return board;
        }else{
            throw new RuntimeException("You are not authorized to delete this board");
        }
    }


}
