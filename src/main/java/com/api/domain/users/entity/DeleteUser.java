package com.api.domain.users.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Getter
@RedisHash("delete")
public class DeleteUser {
    @Id
    private String email;
    private LocalDateTime date;

    public DeleteUser(String email, LocalDateTime date){
        this.email = email;
        this.date = date;
    }
}
