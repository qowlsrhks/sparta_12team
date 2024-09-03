package com.api.domain.boards.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    private String contents;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "member_id")
    private Member memberId;

   @OneToMany(mappedBy = "board")
   private List<Comment> comments = new ArrayList<>();

}
