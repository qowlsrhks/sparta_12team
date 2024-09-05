package com.api.domain.comment.entity;

import com.api.domain.boards.entity.Board;
import com.api.domain.comment.dto.CommentSaveRequestDto;
import com.api.domain.common.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Comment  extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contents;
    private Long userId;
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public Comment(CommentSaveRequestDto requestDto) {
        this.contents = requestDto.getContents();
    }

    public void update(String contents) {
        this.contents = contents;
    }
}
