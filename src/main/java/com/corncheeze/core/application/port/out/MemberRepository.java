package com.corncheeze.core.application.port.out;

import com.corncheeze.core.domain.member.Member;
import com.corncheeze.core.domain.member.NewMember;

import java.util.Optional;

public interface MemberRepository {

    Member save(NewMember newMember);

    Optional<Member> findById(String id);

    Optional<Member> findByNickname(String nickname);
}
