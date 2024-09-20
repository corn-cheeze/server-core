package com.corncheeze.core.adapter.in.web.request;

import com.corncheeze.core.domain.member.Member;
import com.corncheeze.core.domain.member.NewMember;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public class MemberJoinInfo {

    private final String id;

    private final String password;

    private final String nickname;

    public NewMember toNewMember() {
        return new NewMember(new Member.BasicInfo(id, password), new Member.AdditionalInfo(nickname));
    }
}
