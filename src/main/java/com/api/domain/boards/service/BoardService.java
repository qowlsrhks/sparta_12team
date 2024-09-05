package com.api.domain.boards.service;

import com.api.domain.auth.service.AuthService;
import com.api.domain.boards.dto.BoardRequestDto;
import com.api.domain.boards.dto.BoardResponseDto;
import com.api.domain.boards.entity.Board;
import com.api.domain.boards.entity.BoardImage;
import com.api.domain.boards.repository.BoardImageRepository;
import com.api.domain.boards.repository.BoardRepository;
import com.api.domain.common.ReadUtil;
import com.api.domain.users.entity.User;
import com.api.exceptions.ImageUploadFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;
    private final AuthService authService;
    private final ReadUtil readUtil;
    private final BoardImageRepository boardImageRepository;

    //게시글 생성
    @Transactional
    public BoardResponseDto create(String contents, MultipartFile file) {
        Board board = new Board(contents, authService.currentUser());

        if(file != null){
            UUID uuid = UUID.randomUUID();
            String imagePath = System.getenv("image.path");
            imagePath+=File.separator;
            String imageFileName = uuid + "_" + file.getOriginalFilename();
            File destinationFile = new File(imagePath + imageFileName);

            try {
                file.transferTo(destinationFile);
            }catch (IOException e){
                throw new ImageUploadFailedException("이미지 업로드에 실패했습니다.");
            }
            BoardImage image = BoardImage.builder()
                    .url(imagePath + imageFileName)
                    .board(board)
                    .build();
            boardImageRepository.save(image);
            board.addBoardImage(image);
            BoardResponseDto containImageResponseDto= new BoardResponseDto(boardRepository.save(board));
            containImageResponseDto.setImageUrls(image.getUrl());
            return containImageResponseDto;
        }

        Board savedBoard = boardRepository.save(board);
        return new BoardResponseDto(savedBoard);
    }

    //뉴스피드 구현
    public Page<BoardResponseDto> newsfeed(Integer pageNum) {
        //내 기준 친구인 사람들의 리스트
        User currentUser = authService.currentUser();
        List<Board> friendsBoards = new ArrayList<>();
        for(User friend : currentUser.getFriends()) {
            try{List<Board> foundBoards = boardRepository.findAllByUserId(friend.getId());
                friendsBoards.addAll(foundBoards);
            } catch(NullPointerException ignored) {}
        }

        Pageable sortedPageable = readUtil.pageableSortedByModifiedAt(pageNum,10);

        friendsBoards.sort((b1, b2) -> b2.getModifiedAt().compareTo(b1.getModifiedAt()));

        int totalElements = friendsBoards.size();

        int start = (int) sortedPageable.getOffset();
        int end = Math.min((start + sortedPageable.getPageSize()), totalElements);
        List<Board> pageBoards = friendsBoards.subList(start, end);

        List<BoardResponseDto> responseDtos = pageBoards.stream().map(BoardResponseDto::new).toList();

        return new PageImpl<>(responseDtos , sortedPageable, totalElements);
    }

    //특정 유저가 작성한 게시물 다건 조회
    @Transactional
    public Page<BoardResponseDto> findByUserId(Long userId) {
        Pageable sortedPageable = readUtil.pageableSortedByModifiedAt(0,10);
        Page<Board> boards = boardRepository.findAllToPageByUserId(userId , sortedPageable);
        return boards.map(BoardResponseDto::new);
    }

    //   특정 유저의 게시물 단건 조회
    @Transactional
    public Board findByUserIdAndBoardId(Long userId, Long boardId) {
        return boardRepository.findByUserIdAndId(userId, boardId)
                .orElseThrow(() -> new RuntimeException("등록된 게시물을 찾을 수 없습니다"));
    }

    //게시물 전체조회
    @Transactional
    public List<BoardResponseDto> findAll() {
        return boardRepository.findAll().stream().map(BoardResponseDto::new).toList();
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
