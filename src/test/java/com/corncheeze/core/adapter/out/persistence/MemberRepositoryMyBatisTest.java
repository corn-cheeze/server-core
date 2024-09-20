package com.corncheeze.core.adapter.out.persistence;

import com.corncheeze.core.application.port.out.MemberRepository;
import com.corncheeze.core.common.config.MemberConfig;
import com.corncheeze.core.common.config.MyBatisConfig;
import com.corncheeze.core.domain.member.Member;
import com.corncheeze.core.domain.member.NewMember;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {MyBatisConfig.class, MemberConfig.class})
class MemberRepositoryMyBatisTest {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberMapper.deleteAll();
    }

    @Test
    void Save_a_member() {
        NewMember newMember = defaultNewMember();

        Member savedMember = memberRepository.save(newMember);

        Member memberForTest = new Member(null, defaultBasicInfo(), defaultAdditionalInfo(), null);
        assertThat(savedMember).isNotNull();
        assertThat(savedMember)
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

    @Test
    void Id_must_be_unique() {
        NewMember newMember1 = new NewMember(defaultBasicInfo(), new Member.AdditionalInfo("nickname1"));
        memberRepository.save(newMember1);
        NewMember newMember2 = new NewMember(defaultBasicInfo(), new Member.AdditionalInfo("nickname2"));

        assertThatThrownBy(() -> memberRepository.save(newMember2))
                .isInstanceOf(DuplicateKeyException.class);
    }

    @Test
    void Nickname_must_be_unique() {
        NewMember newMember1 = new NewMember(new Member.BasicInfo("id1", defaultPassword()), defaultAdditionalInfo());
        memberRepository.save(newMember1);
        NewMember newMember2 = new NewMember(new Member.BasicInfo("id2", defaultPassword()), defaultAdditionalInfo());

        assertThatThrownBy(() -> memberRepository.save(newMember2))
                .isInstanceOf(DuplicateKeyException.class);
    }

    @Test
    void Find_a_member_by_id() {
        NewMember newMember = defaultNewMember();
        memberRepository.save(newMember);

        Optional<Member> optionalMember = memberRepository.findById(newMember.getId());

        Member memberForTest = new Member(null, defaultBasicInfo(), defaultAdditionalInfo(), null);
        assertThat(optionalMember).isPresent();
        Member findMember = optionalMember.get();
        assertThat(findMember)
                .usingRecursiveComparison()
                .ignoringFields("seq", "dataHistory")
                .isEqualTo(memberForTest);
    }

    @Test
    void Find_empty_by_id() {
        NewMember newMember = defaultNewMember();

        Optional<Member> optionalMember = memberRepository.findById(newMember.getId());

        assertThat(optionalMember).isEmpty();
    }

    @Test
    void Find_a_member_by_nickname() {
        NewMember newMember = defaultNewMember();
        memberRepository.save(newMember);

        Optional<Member> optionalMember = memberRepository.findByNickname(newMember.getNickname());

        Member memberForTest = new Member(null, defaultBasicInfo(), defaultAdditionalInfo(), null);
        assertThat(optionalMember).isPresent();
        Member findMember = optionalMember.get();
        assertThat(findMember)
                .usingRecursiveComparison()
                .ignoringFields("seq", "dataHistory")
                .isEqualTo(memberForTest);
    }

    @Test
    void Find_empty_by_nickname() {
        NewMember newMember = defaultNewMember();

        Optional<Member> optionalMember = memberRepository.findByNickname(newMember.getNickname());

        assertThat(optionalMember).isEmpty();
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