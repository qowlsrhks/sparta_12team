package com.api.domain.boards.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@Getter
@Entity
@NoArgsConstructor
public class Comment  extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contents;
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public Comment(String contents, String username, Board board) {
        this.contents = contents;
        this.username = username;
        this.board = board;

    }

    public void update(String contents) {
        this.contents = contents;
    }
}
