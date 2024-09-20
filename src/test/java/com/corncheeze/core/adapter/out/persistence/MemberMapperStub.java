package com.corncheeze.core.adapter.out.persistence;

import com.corncheeze.core.domain.datahistory.DataHistory;
import com.corncheeze.core.domain.member.Member;
import com.corncheeze.core.domain.member.NewMember;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class MemberMapperStub implements MemberMapper {

    private Map<Long, Member> members = new HashMap<>();

    private Long seq = 1L;

    @Override
    public void deleteAll() {
        members.clear();
    }

    @Override
    public int insertMember(NewMember newMember) {
        Member member = new Member(seq, new Member.BasicInfo(newMember.getId(), newMember.getPassword()), new Member.AdditionalInfo(newMember.getNickname()), new DataHistory("user1", "app1", LocalDateTime.now(), "user1", "app1", LocalDateTime.now()));

        members.put(seq, member);

        seq++;

        return 1;
    }

    @Override
    public Member selectMemberById(String id) {
        for (Long seq : members.keySet()) {
            Member member = members.get(seq);

            if (member.getId().equals(id)) {
                return member;
            }
        }

        return null;
    }

    @Override
    public Member selectMemberByNickname(String nickname) {
        for (Long seq : members.keySet()) {
            Member member = members.get(seq);

            if (member.getNickname().equals(nickname)) {
                return member;
            }
        }

        return null;
    }
}
