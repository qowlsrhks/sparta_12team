package com.api.domain.boards.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MemberDto {
    private Long memberId;
    private String username;
    private String password;
    private String email;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private List<BoardDto> boardId;

}
