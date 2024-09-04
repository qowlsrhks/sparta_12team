package com.api.domain.users.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@RedisHash("user")
public class Email {
    @Id
    private String email;
    private int authNum;

    @TimeToLive
    private int ttl; // 만료 시간, 시간 지난 후 redis에서 자동 삭제

    public Email(String email, int authNum, int ttl){
        this.email = email;
        this.authNum = authNum;
        this.ttl = ttl;
    }
}
