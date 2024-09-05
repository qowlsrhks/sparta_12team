package com.api.domain.users.entity;

import com.api.domain.common.Timestamped;
import com.api.domain.users.dto.UserCreateRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
@NoArgsConstructor
public class User extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

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

    @ManyToMany
    @JoinTable(
            name = "user_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private List<User> friends = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "user_friend_requests",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private List<User> friendRequests = new ArrayList<>();

    public User(UserCreateRequestDto requestDto){
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
        this.isMember = false;
    }
}
