package com.api.domain.boards.service;


import com.api.domain.boards.entity.BoardImage;
import com.api.domain.boards.repository.BoardImageRepository;
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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final BoardImageRepository boardImageRepository;


    //    게시글 생성
    @Transactional
    public BoardDto createBoard(Long userId, String contents, MultipartFile file) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new RuntimeException("회원이 존재하지 않습니다."));
        Board board = new Board(contents, user);
        BoardDto boardDto = null;

        if(file != null){
                UUID uuid = UUID.randomUUID();
                String imageFileName = uuid + "_" + file.getOriginalFilename();
                File destinationFile = new File("C:\\Users\\zkdlt\\OneDrive\\Desktop\\sparta_12team\\photos" + imageFileName);

                try {
                    file.transferTo(destinationFile);
                }catch (IOException e){
                    throw new RuntimeException(e);
                }
                BoardImage image = BoardImage.builder()
                        .url("C:\\Users\\zkdlt\\OneDrive\\Desktop\\sparta_12team\\photos" + imageFileName)
                        .board(board)
                        .build();
                boardImageRepository.save(image);
                boardDto = new BoardDto(boardRepository.save(board));
                boardDto.changeImageUrl(image.getUrl());
        }

        return boardDto;
    }

    //    뉴스피드 목록
    public Page<List<Board>> getFriendBoardList(Long userId, int page) {
        Sort sort = Sort.by("createAt").descending();
        Pageable pageable = PageRequest.of(page, 10, sort);
        return boardRepository.findBoardsByUserFriends(userId, pageable);
    }


    //유저 게시글 목록
    public Page<Board> userBoardList(Long userId, int page) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("등록된 회원을 찾을 수 없습니다"));
        Pageable pageable = PageRequest.of(page, 10);

        return boardRepository.findAllByUser(user, pageable);
    }

    //    유저 게시글 조회
    public Board userBoard(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException("게시글가 없습니다"));


    }

    //    모든 게시글 목록
    public Page<Board> boardAllList(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return boardRepository.findAll(pageable);
    }

    //    게시글 수정
    @Transactional
    public Board updateBoard(Long userId, Long boardId, BoardDto boardDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("등록된 회원을 찾을 수 없습니다"));
        Board board = boardRepository.findByUserAndBoardId(user, boardId)
                .orElseThrow(() -> new RuntimeException("등록된 게시판을 찾을 수 없습니다"));
        if (board.getUser().getUserId().equals(userId)) {
            board.update(boardDto.getContents());
            return board;
        } else {
            throw new RuntimeException("You are not authorized to update this board");
        }
    }

    //    게시글 삭제
    @Transactional
    public Board deleteBoard(Long userId, Long boardId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("등록된 회원을 찾을 수 없습니다"));
        Board board = boardRepository.findByUserAndBoardId(user, boardId)
                .orElseThrow(() -> new RuntimeException("등록된 게시판을 찾을 수 없습니다"));
        if (board.getUser().getUserId().equals(userId)) {
            boardRepository.delete(board);
            return board;
        } else {
            throw new RuntimeException("You are not authorized to delete this board");
        }
    }



}
