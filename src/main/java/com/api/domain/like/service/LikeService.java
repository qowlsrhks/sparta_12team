package com.api.domain.like.service;

import com.api.domain.boards.dto.BoardResponseDto;
import com.api.domain.boards.entity.Board;
import com.api.domain.boards.repository.BoardRepository;
import com.api.domain.like.entity.Like;
import com.api.domain.like.repository.LikeRepository;
import com.api.domain.users.dto.UserResponseDto;
import com.api.domain.users.entity.User;
import com.api.domain.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final LikeRepository likeRepository;

    //좋아요 추가
    @Transactional
    public BoardResponseDto liked(Long boardId) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        if(likeRepository.findByUserEmailAndBoardId(currentUserEmail, boardId).isPresent()) {
            throw new IllegalArgumentException("이미 '좋아요'된 글입니다!");
        }

        User currentUser = userRepository.findByEmail(currentUserEmail).orElseThrow();
        Board foundBoard = boardRepository.findById(boardId).orElseThrow();

        Like like = new Like();
        like.setUser(currentUser);
        like.setBoard(foundBoard);

        foundBoard.setLikesCount(foundBoard.getLikesCount() + 1);

        likeRepository.save(like);
        Board savedboard = boardRepository.save(foundBoard);
        return new BoardResponseDto(savedboard);
    }

    @Transactional
    //좋아요 취소
    public BoardResponseDto cancelLike(Long boardId) {
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Like foundLike = likeRepository.findByUserEmailAndBoardId(currentEmail, boardId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시물은 좋아요 상태가 아닙니다!")
        );

        Board foundBoard = boardRepository.findById(boardId).orElseThrow();
        foundBoard.setLikesCount(foundBoard.getLikesCount() - 1);
        Board savedboard = boardRepository.save(foundBoard);
        likeRepository.delete(foundLike);
        return new BoardResponseDto(savedboard);
    }

    @Transactional
    //해당 게시물에 좋아요 누른 유저 목록 조회
    public List<UserResponseDto> whoLiked (Long boardId) {
        List<Like> likes = likeRepository.findByBoardId(boardId);

        List<User> likedUsers = new ArrayList<>();
        for(Like like : likes) {
            likedUsers.add(like.getUser());
        }

        return likedUsers.stream().map(UserResponseDto::new).toList();
    }

    @Transactional
    //해당 유저의 좋아요 목록을 조회
    public List<BoardResponseDto> whatLiked (Long userId) {
        List<Like> likes = likeRepository.findByUserId(userId);

        List<Board> likedBoard = new ArrayList<>();
        for(Like like : likes) {
            likedBoard.add(like.getBoard());
        }

        return likedBoard.stream().map(BoardResponseDto::new).toList();
    }
}
