package com.api.domain.users.entity;

import com.api.domain.boards.entity.Board;
import com.api.domain.boards.entity.Friend;
import com.api.domain.common.Timestamped;
import com.api.domain.users.dto.UserRequestDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
@NoArgsConstructor
public class User extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "username", nullable = false, length = 20)
    private String username;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "introduce", length = 100)
    private String introduce;

    // true : 활성 회원
    // false : 비활성 회원
    @Column(nullable = false)
    private boolean isMember = true;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Board> boards;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Friend> friends;


    public User(UserRequestDto requestDto){
        this.username = requestDto.getUsername();
        this.email = requestDto.getEmail();
        this.password = requestDto.getPassword();
        this.introduce = requestDto.getIntroduce();
    }

    public void update(String username, String introduce, String password) {
        this.username = username;
        this.introduce = introduce;
        this.password = password;
    }

    public void withdrawUser(){
        this.isMember = !this.isMember;
    }
}
