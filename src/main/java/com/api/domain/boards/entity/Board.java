package com.api.domain.boards.entity;

import com.api.domain.common.Timestamped;
import com.api.domain.users.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor
public class Board extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User userId;

    private LocalDateTime createdAt;


    public Board(String contents, User userId) {
        this.contents = contents;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
    }
}
