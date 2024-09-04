package com.api.domain.like.service;

import com.api.domain.boards.entity.Board;
import com.api.domain.boards.repository.BoardRepository;
import com.api.domain.like.entity.Like;
import com.api.domain.like.repository.LikeRepository;
import com.api.domain.users.entity.User;
import com.api.domain.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final LikeRepository likeRepository;

    //좋아요 추가
    public void liked(Long boardId) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(currentUserEmail);

        if(likeRepository.findByUserEmailAndBoardId(currentUserEmail, boardId).isPresent()) {
            throw new IllegalArgumentException("이미 '좋아요'된 글입니다!");
        }

        User currentUser = userRepository.findByEmail(currentUserEmail).orElseThrow();
        Board foundBoard = boardRepository.findById(boardId).orElseThrow();

        Like like = new Like();
        like.setUser(currentUser);
        like.setBoard(foundBoard);

        foundBoard.setLikesCount(like.getBoard().getLikesCount() + 1);

        likeRepository.save(like);
        boardRepository.save(foundBoard);
    }
}
