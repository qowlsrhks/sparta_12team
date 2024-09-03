package com.api.domain.boards.dto;

import com.api.domain.boards.common.Timestamped;
import com.api.domain.boards.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDto extends Timestamped {

    private Long boardId;

    private String contents;

    private Member memberId;

}
