package com.api.domain.boards.service;


import com.api.domain.auth.service.AuthService;
import com.api.domain.boards.dto.BoardDto;
import com.api.domain.boards.entity.Board;
import com.api.domain.boards.repository.BoardRepository;
import com.api.domain.users.entity.User;
import com.api.domain.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    User currentUser = authService.currentUser();

    //게시글 생성
    @Transactional
    public Board create(BoardDto boardDto) {
        Board board = new Board(boardDto.getContents(), currentUser);
        return boardRepository.save(board);
    }

////    뉴스피드 목록(미구현)
//    @Transactional
//    public Page<Board> getFriendBoardList(Long userId , int page) {
//        Optional<User> user = userRepository.findById(userId);
//        if(user.isPresent()){
//            Pageable pageable = PageRequest.of(page, 10);
//            return boardRepository.findByUserId(user.get(), pageable);
//        }else{
//            throw new RuntimeException("등록된 회원이 없습니다");
//        }
//    }

////    뉴스피드 조회(미구현)
//    @Transactional
//    public Page<Board> getFriendBoard(Long userId, int page) {
//        Optional<User> user = userRepository.findById(userId);
//        if(user.isPresent()){
//            Pageable pageable = PageRequest.of(page, 10);
//            return null;
//        }else {
//            throw new RuntimeException("등록된 회원이 없습니다");
//        }
//    }

    //특정 유저가 작성한 게시물 다건 조회
    @Transactional
    public List<Board> findBoardsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NullPointerException("등록된 회원을 찾을 수 없습니다"));

        return boardRepository.findBoardsByUser(user);
    }

    //   특정 유저의 게시물 단건 조회
    @Transactional
    public Board findByUserIdAndBoardId(Long userId, Long boardId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("등록된 회원을 찾을 수 없습니다"));

        return boardRepository.findByUserAndBoardId(user, boardId)
                .orElseThrow(() -> new RuntimeException("등록된 게시판을 찾을 수 없습니다"));
    }

    //    게시물 전체조회
    @Transactional
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    //    게시글 수정
    @Transactional
    public Board update(Long boardId, BoardDto boardDto) {
        Board foundBoard = boardRepository.findById(boardId).orElseThrow(
                ()->new NullPointerException("게시물을 찾을 수 없습니다.")
        );

        //작성자와 요청자가 같은지 확인
        if(!authService.isUserOwner(foundBoard.getUser())) {
            throw new AccessDeniedException("작성자만 수정할 수 있습니다.");
        }

        foundBoard.setContents(boardDto.getContents());
        return boardRepository.save(foundBoard);
    }

    //    게시글 삭제
    @Transactional
    public String delete(Long boardId) {
        Board foundBoard = boardRepository.findById(boardId).orElseThrow(
                () -> new NullPointerException("게시물을 찾을 수 없습니다.")
        );

        //작성자와 요청자가 같은지 확인
        if(!authService.isUserOwner(foundBoard.getUser())) {
            throw new AccessDeniedException("작성자만 삭제할 수 있습니다.");
        }

        boardRepository.delete(foundBoard);
        return "삭제 완료";
    }
}
