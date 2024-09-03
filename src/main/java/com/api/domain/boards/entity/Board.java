package com.api.domain.boards.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

}
