package com.api.domain.boards.entity;

import com.api.domain.boards.common.Timestamped;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
public class Member extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String username;

    private String password;

    private String email;

}
