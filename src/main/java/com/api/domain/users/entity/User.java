package com.api.domain.users.entity;

import com.api.domain.common.Timestamped;
import com.api.domain.users.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

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

    @Column(name = "password", nullable = false, length = 20)
    private String password;

    @Column(name = "introduce", length = 100)
    //@ColumnDefault(" ")
    private String introduce;

    public User(UserRequestDto requestDto){
        this.username = requestDto.getUsername();
        this.email = requestDto.getEmail();
        this.password = requestDto.getPassword();
        this.introduce = requestDto.getIntroduce();
    }
}
