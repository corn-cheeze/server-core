package com.corncheeze.core.adapter.out.persistence;

import com.corncheeze.core.application.port.out.MemberRepository;
import com.corncheeze.core.domain.member.Member;
import com.corncheeze.core.domain.member.NewMember;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class MemberRepositoryMyBatis implements MemberRepository {

    private final MemberMapper memberMapper;

    @Override
    public Member save(NewMember newMember) {
        memberMapper.insertMember(newMember);

        return findById(newMember.getId())
                .orElseThrow(() -> new IllegalStateException("Didn't find member with NewMember: " + newMember));
    }

    @Override
    public Optional<Member> findById(String id) {
        Member member = memberMapper.selectMemberById(id);

        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByNickname(String nickname) {
        Member member = memberMapper.selectMemberByNickname(nickname);

        return Optional.ofNullable(member);
    }
}
