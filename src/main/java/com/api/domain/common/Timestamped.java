package com.api.domain.common;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Timestamped {

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    LocalDateTime createAt;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    LocalDateTime modifiedAt;
}
