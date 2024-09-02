package com.api.domain.users.entity;

import com.api.domain.common.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
@NoArgsConstructor
public class User extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId;

    @Column(name = "email", nullable = false, length = 50)
    String email;

    @Column(name = "username", nullable = false, length = 20)
    String username;

    @Column(name = "password", nullable = false, length = 20)
    String password;

    @Column(name = "introduce", length = 100)
    String introduce;
}
