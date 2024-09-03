package com.api.domain.boards.service;

import com.api.domain.boards.dto.comment.CommentResponseDto;
import com.api.domain.boards.dto.comment.CommentSaveRequestDto;
import com.api.domain.boards.dto.comment.CommentSaveResponseDto;
import com.api.domain.boards.entity.Board;
import com.api.domain.boards.entity.Comment;
import com.api.domain.boards.repository.BoardRepository;
import com.api.domain.boards.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    // 댓클 작성
    public CommentSaveResponseDto saveComment(Long boardId, CommentSaveRequestDto commentSaveRequestDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NullPointerException("보드가 읍습니다"));

        Comment newComment = new Comment(commentSaveRequestDto.getContents(), board);
        Comment savedComment = commentRepository.save(newComment);

        return new CommentSaveResponseDto(savedComment.getId(), savedComment.getContents());
    }

    // 조회
    public List<CommentResponseDto> getComments() {

        List<Comment> commentList = commentRepository.findAll();

        List<CommentResponseDto> dtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            dtoList.add(new CommentResponseDto(comment.getId(), comment.getContents()));
        }
        return dtoList;
    }


}
