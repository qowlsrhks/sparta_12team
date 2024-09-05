package com.api.domain.users.util;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

/**
 * 게시글,댓글 등을 조회할 때 공통적으로 사용되는 유틸리티 메서드들을 정의한 유틸리티 메서드입니다.
 */
@Component
public class ReadUtil {
    public Pageable pageableSortedByModifiedAt (Integer page, Integer size) {
        int PageNum = (page != null && page >= 0) ? page : 0;
        int PageSize = (size != null && size > 0) ? size : 10;
        Sort sort = Sort.by(Sort.Direction.DESC, "modifiedAt");

        return PageRequest.of(PageNum,PageSize,sort);
    }
}
