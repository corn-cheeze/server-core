package com.corncheeze.core.application.port.in;

import com.corncheeze.core.domain.member.Member;
import com.corncheeze.core.domain.member.NewMember;

public interface UserService {

    boolean existsById(String id);

    boolean existsByNickname(String nickname);

    Member join(NewMember newMember);
}
