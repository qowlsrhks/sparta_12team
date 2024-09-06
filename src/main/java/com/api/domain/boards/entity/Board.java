package com.api.domain.boards.entity;

import com.api.domain.comment.entity.Comment;
import com.api.domain.common.Timestamped;
import com.api.domain.users.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Board extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String contents;
    private Long likesCount = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @OneToOne(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private BoardImage boardImage;


   @OneToMany(mappedBy = "board")
   private List<Comment> comments = new ArrayList<>();

    public Board(String contents, User user) {
        this.contents = contents;
        this.user = user;
    }

    public void addBoardImage(BoardImage boardImage) {
        this.boardImage = boardImage;
    }

    public Optional<BoardImage> getBoardImage() {
        return Optional.ofNullable(boardImage);
    }
}
