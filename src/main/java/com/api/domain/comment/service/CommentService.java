package com.api.domain.comment.service;

import com.api.domain.auth.service.AuthService;
import com.api.domain.boards.entity.Board;
import com.api.domain.boards.repository.BoardRepository;
import com.api.domain.comment.dto.CommentSaveRequestDto;
import com.api.domain.comment.dto.CommentResponseDto;
import com.api.domain.comment.dto.CommentUpdateRequestDto;
import com.api.domain.comment.entity.Comment;
import com.api.domain.comment.repository.CommentRepository;
import com.api.domain.users.entity.User;
import com.api.domain.users.repository.UserRepository;
import com.api.domain.users.util.ReadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final ReadUtil readUtil;
    private final AuthService authService;
    private final UserRepository userRepository;

    //댓글 저장
    @Transactional
    public CommentResponseDto save(Long boardId, CommentSaveRequestDto requestDto) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        Comment comment = new Comment(requestDto);
        comment.setBoard(board);
        comment.setUsername(authService.currentUser().getUsername());
        comment.setUserId(authService.currentUser().getId());
        Comment savedComment = commentRepository.save(comment);
        return new CommentResponseDto(savedComment);
    }

    //댓글 다건 조회
    public Page<CommentResponseDto> findAllByBoardId(Long boardId, Integer page, Integer size) {
        Pageable sortedPageable = readUtil.pageableSortedByModifiedAt(page, size);
        return commentRepository.findAllByBoardId(boardId, sortedPageable).map(CommentResponseDto::new);
    }

    //댓글 단건 조회
    public CommentResponseDto findByCommentId(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NullPointerException("댓글을 찾을 수 없습니다."));
        return new CommentResponseDto(comment);
    }

    //댓글 수정
    @Transactional
    public CommentResponseDto update(Long commentId, CommentUpdateRequestDto commentUpdateRequestDto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NullPointerException("댓글을 찾을 수 없습니다."));

        //작성자와 요청자가 같은지 확인
        User foundUser = userRepository.findById(comment.getUserId()).orElseThrow();
        if (!authService.isUserOwner(foundUser)) {
            throw new AccessDeniedException("작성자만 수정할 수 있습니다.");
        }

        comment.update(commentUpdateRequestDto.getContents());
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    //댓글 삭제
    @Transactional
    public CommentResponseDto delete(Long commentId) {
        Comment comment = findById(commentId);

        //작성자와 요청자가 같은지 확인
        User foundUser = userRepository.findByUsername(comment.getUsername()).orElseThrow(() ->
                new IllegalArgumentException("작성자를 찾을 수 없습니다."));

        if (!authService.isUserOwner(foundUser)) {
            throw new AccessDeniedException("작성자만 삭제할 수 있습니다.");
        }
        CommentResponseDto responseDto = new CommentResponseDto(comment);
        commentRepository.delete(comment);

        return responseDto;
    }

    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new NullPointerException("댓글을 찾을 수 없습니다."));
    }
}
