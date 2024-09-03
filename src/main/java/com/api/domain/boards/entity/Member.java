package com.api.domain.boards.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String username;

    private String password;

    private String email;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

}
