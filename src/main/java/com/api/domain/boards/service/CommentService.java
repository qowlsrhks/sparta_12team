package com.api.domain.boards.service;

import com.api.domain.boards.dto.comment.*;
import com.api.domain.boards.entity.Board;
import com.api.domain.boards.entity.Comment;
import com.api.domain.boards.entity.Member;
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

    @Transactional
    public CommentSaveResponseDto saveComment(Long boardId, CommentSaveRequestDto commentSaveRequestDto) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Comment newComment = new Comment(
                commentSaveRequestDto.getContents(),
                commentSaveRequestDto.getUsername(),
                board
                );

        Comment savedComment = commentRepository.save(newComment);

        return new CommentSaveResponseDto(savedComment.getId(), savedComment.getContents(), savedComment.getUsername());
    }

    public List<CommentDetailResponseDto> getComments(Long boardId) {
        List<Comment> commentList = commentRepository.findByBoardId(boardId);

        List<CommentDetailResponseDto> dtoList = new ArrayList<>();

        for (Comment comment : commentList) {
            CommentDetailResponseDto dto = new CommentDetailResponseDto(
                    comment.getId(),
                    comment.getContents(),
                    comment.getUsername()
            );
            dtoList.add(dto);
        }
        return dtoList;
    }

    public CommentDetailResponseDto getComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NullPointerException("코멘트가 없습니다"));

        return new CommentDetailResponseDto(
                comment.getId(), comment.getContents(), comment.getUsername()
        );
    }

    public CommentUpdateResponseDto updateComment(Long commentId, CommentUpdateRequestDto commentUpdateRequestDto) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new NullPointerException("코멘트가 없습니다"));

        comment.update(commentUpdateRequestDto.getContents());
        return new CommentUpdateResponseDto(comment.getId(), comment.getContents());
    }

    @Transactional
    public void deleteComment(Long commentId) {

        if(commentRepository.existsById(commentId)) {
            throw new NullPointerException("코멘트가 없습니다");
        }

        commentRepository.deleteById(commentId);

    }

//    // 댓클 작성
//    public CommentSaveResponseDto saveComment(Long boardId, CommentSaveRequestDto commentSaveRequestDto) {
//        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NullPointerException("보드가 읍습니다"));
//
//        Comment newComment = new Comment(commentSaveRequestDto.getContents(), board);
//        Comment savedComment = commentRepository.save(newComment);
//
//        return new CommentSaveResponseDto(savedComment.getId(), savedComment.getContents());
//    }
//
//    // 조회
//    public List<CommentResponseDto> getComments() {
//
//        List<Comment> commentList = commentRepository.findAll();
//
//        List<CommentResponseDto> dtoList = new ArrayList<>();
//        for (Comment comment : commentList) {
//            dtoList.add(new CommentResponseDto(comment.getId(), comment.getContents()));
//        }
//        return dtoList;
//    }


}
