package com.api.domain.boards.dto;

import com.api.domain.boards.common.Timestamped;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MemberDto extends Timestamped {
    private Long memberId;
    private String username;
    private String password;
    private String email;
    private List<BoardDto> boardId;

}
