package com.corncheeze.core.application.service;

import com.corncheeze.core.adapter.out.persistence.MemberMapperStub;
import com.corncheeze.core.adapter.out.persistence.MemberRepositoryMyBatis;
import com.corncheeze.core.application.port.in.UserService;
import com.corncheeze.core.application.port.out.MemberRepository;
import com.corncheeze.core.domain.member.Member;
import com.corncheeze.core.domain.member.NewMember;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserServiceImplTest {

    private MemberRepository memberRepository;

    private UserService userService;

    @BeforeEach
    void setUp() {
        memberRepository = new MemberRepositoryMyBatis(new MemberMapperStub());
        userService = new UserServiceImpl(memberRepository);
    }

    @Test
    void Exists_member_by_id() {
        NewMember newMember = defaultNewMember();
        userService.join(newMember);

        boolean exists = userService.existsById(newMember.getId());

        assertThat(exists).isTrue();
    }

    @Test
    void Not_exists_member_by_id() {
        NewMember newMember = defaultNewMember();

        boolean exists = userService.existsById(newMember.getId());

        assertThat(exists).isFalse();
    }

    @Test
    void Exists_member_by_nickname() {
        NewMember newMember = defaultNewMember();
        userService.join(newMember);

        boolean exists = userService.existsByNickname(newMember.getNickname());

        assertThat(exists).isTrue();
    }

    @Test
    void Not_exists_member_by_nickname() {
        NewMember newMember = defaultNewMember();

        boolean exists = userService.existsByNickname(newMember.getNickname());

        assertThat(exists).isFalse();
    }

    @Test
    void Id_must_be_unique() {
        NewMember newMember1 = new NewMember(defaultBasicInfo(), new Member.AdditionalInfo("nickname1"));
        userService.join(newMember1);
        NewMember newMember2 = new NewMember(defaultBasicInfo(), new Member.AdditionalInfo("nickname2"));

        assertThatThrownBy(() -> userService.join(newMember2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Id already exists. current id is " + newMember2.getId());
    }

    @Test
    void Nickname_must_be_unique() {
        NewMember newMember1 = new NewMember(new Member.BasicInfo("id1", defaultPassword()), defaultAdditionalInfo());
        userService.join(newMember1);
        NewMember newMember2 = new NewMember(new Member.BasicInfo("id2", defaultPassword()), defaultAdditionalInfo());

        assertThatThrownBy(() -> userService.join(newMember2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Nickname already exists. current nickname is " + newMember2.getNickname());
    }

    @Test
    void Join_a_member() {
        NewMember newMember = defaultNewMember();

        Member joinedMember = userService.join(newMember);

        Member memberForTest = new Member(null, defaultBasicInfo(), defaultAdditionalInfo(), null);
        assertThat(joinedMember).isNotNull();
        assertThat(joinedMember)
                .usingRecursiveComparison()
                .ignoringFields("seq", "dataHistory")
                .isEqualTo(memberForTest);
        Optional<Member> optionalMember = memberRepository.findById(newMember.getId());
        assertThat(optionalMember).isPresent();
        Member findMember = optionalMember.get();
        assertThat(findMember)
                .usingRecursiveComparison()
                .ignoringFields("seq", "dataHistory")
                .isEqualTo(memberForTest);
    }

    private NewMember defaultNewMember() {
        return new NewMember(defaultBasicInfo(), defaultAdditionalInfo());
    }

    private Member.AdditionalInfo defaultAdditionalInfo() {
        return new Member.AdditionalInfo(defaultNickname());
    }

    private Member.BasicInfo defaultBasicInfo() {
        return new Member.BasicInfo(defaultId(), defaultPassword());
    }

    private String defaultId() {
        return "cc";
    }

    private String defaultPassword() {
        return "1q2w3e4r!@";
    }

    private String defaultNickname() {
        return "cheeze";
    }
}