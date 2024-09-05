package com.api.domain.boards.entity;

import com.api.domain.comment.entity.Comment;
import com.api.domain.common.Timestamped;
import com.api.domain.users.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Board extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String contents;
    private Long likesCount = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;


   @OneToMany(mappedBy = "board")
   private List<Comment> comments = new ArrayList<>();

    public Board(String contents, User user) {
        this.contents = contents;
        this.user = user;
    }
}
