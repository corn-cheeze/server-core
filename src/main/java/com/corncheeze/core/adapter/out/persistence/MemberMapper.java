package com.corncheeze.core.adapter.out.persistence;

import com.corncheeze.core.domain.member.Member;
import com.corncheeze.core.domain.member.NewMember;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    void deleteAll();

    int insertMember(NewMember member);

    Member selectMemberById(String id);

    Member selectMemberByNickname(String nickname);
}
