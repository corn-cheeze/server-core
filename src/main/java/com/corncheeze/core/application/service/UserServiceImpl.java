package com.corncheeze.core.application.service;

import com.corncheeze.core.application.port.in.UserService;
import com.corncheeze.core.application.port.out.MemberRepository;
import com.corncheeze.core.domain.member.Member;
import com.corncheeze.core.domain.member.NewMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final MemberRepository memberRepository;

    @Override
    public boolean existsById(String id) {
        return memberRepository.findById(id).isPresent();
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return memberRepository.findByNickname(nickname).isPresent();
    }

    @Override
    public Member join(NewMember newMember) {
        verifyIdAndNickname(newMember);

        Member member = memberRepository.save(newMember);
        log.info("New Member: {}", member);

        /**
         * @TODO DuplicateKeyException 처리 필요
         */

        return member;
    }

    private void verifyIdAndNickname(NewMember newMember) {
        if (existsById(newMember.getId())) {
            throw new IllegalArgumentException("Id already exists. current id is " + newMember.getId());
        }

        if (existsByNickname(newMember.getNickname())) {
            throw new IllegalArgumentException("Nickname already exists. current nickname is " + newMember.getNickname());
        }
    }
}
