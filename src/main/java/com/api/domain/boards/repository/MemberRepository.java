package com.api.domain.boards.repository;

import com.api.domain.boards.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findBymemberId(Long memberId);
}
