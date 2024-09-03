package com.api.domain.boards.entity;

import com.api.domain.boards.common.Timestamped;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;


@Entity
@Setter
@Getter
public class Board extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    private String contents;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "member_id")
    private Member memberId;


}
