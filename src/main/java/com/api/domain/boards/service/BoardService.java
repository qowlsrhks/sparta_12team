package com.api.domain.boards.service;

import com.api.domain.auth.service.AuthService;
import com.api.domain.boards.dto.BoardRequestDto;
import com.api.domain.boards.dto.BoardResponseDto;
import com.api.domain.boards.entity.Board;
import com.api.domain.boards.repository.BoardRepository;
import com.api.domain.users.util.ReadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;
    private final AuthService authService;
    private final ReadUtil readUtil;

    //게시글 생성
    @Transactional
    public BoardResponseDto create(BoardRequestDto boardRequestDto) {
        Board board = new Board(boardRequestDto.getContents(), authService.currentUser());
        Board savedBoard = boardRepository.save(board);
        return new BoardResponseDto(savedBoard);
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
    public List<Board> findByUserId(Long userId) {
        return boardRepository.findByUserId(userId);
    }

    //   특정 유저의 게시물 단건 조회
    @Transactional
    public Board findByUserIdAndBoardId(Long userId, Long boardId) {
        return boardRepository.findByUserIdAndId(userId, boardId)
                .orElseThrow(() -> new RuntimeException("등록된 게시물을 찾을 수 없습니다"));
    }

    //게시물 전체조회
    @Transactional
    public Page<BoardResponseDto> findAll(Integer page, Integer size) {
        Pageable sortedPageable = readUtil.pageableSortedByModifiedAt(page,size);
        return boardRepository.findAll(sortedPageable).map(BoardResponseDto::new);
    }

    //    게시글 수정
    @Transactional
    public Board update(Long boardId, BoardRequestDto boardRequestDto) {
        Board foundBoard = boardRepository.findById(boardId).orElseThrow(
                ()->new NullPointerException("게시물을 찾을 수 없습니다.")
        );

        //작성자와 요청자가 같은지 확인
        if(!authService.isUserOwner(foundBoard.getUser())) {
            throw new AccessDeniedException("작성자만 수정할 수 있습니다.");
        }

        foundBoard.setContents(boardRequestDto.getContents());
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
