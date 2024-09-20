package com.corncheeze.core.adapter.out.persistence;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {MyBatisConfig.class})
class MemberMapperTest {

    @Autowired
    private MemberMapper memberMapper;

    @BeforeEach
    void setUp() {
        memberMapper.deleteAll();
    }

    @Test
    void Delete_all() {
        NewMember newMember = defaultNewMember();
        memberMapper.insertMember(newMember);

        memberMapper.deleteAll();

        Member member = memberMapper.selectMemberById(newMember.getId());
        assertThat(member).isNull();
    }

    @Test
    void Insert_a_new_member() {
        NewMember newMember = defaultNewMember();

        int count = memberMapper.insertMember(newMember);

        assertThat(count).isEqualTo(1);
        Member findMember = memberMapper.selectMemberById(newMember.getId());
        assertThat(findMember).isNotNull();
        Member memberForTest = new Member(null, defaultBasicInfo(), defaultAdditionalInfo(), null);
        assertThat(findMember)
                .usingRecursiveComparison()
                .ignoringFields("seq", "dataHistory")
                .isEqualTo(memberForTest);
    }

    @Test
    void Id_must_be_unique() {
        NewMember newMember1 = new NewMember(defaultBasicInfo(), new Member.AdditionalInfo("nickname1"));
        memberMapper.insertMember(newMember1);
        NewMember newMember2 = new NewMember(defaultBasicInfo(), new Member.AdditionalInfo("nickname2"));

        assertThatThrownBy(() -> memberMapper.insertMember(newMember2))
                .isInstanceOf(DuplicateKeyException.class);
    }

    @Test
    void Nickname_must_be_unique() {
        NewMember newMember1 = new NewMember(new Member.BasicInfo("id1", defaultPassword()), defaultAdditionalInfo());
        memberMapper.insertMember(newMember1);
        NewMember newMember2 = new NewMember(new Member.BasicInfo("id2", defaultPassword()), defaultAdditionalInfo());

        assertThatThrownBy(() -> memberMapper.insertMember(newMember2))
                .isInstanceOf(DuplicateKeyException.class);
    }

    @Test
    void Select_a_member_by_id() {
        NewMember newMember = defaultNewMember();
        memberMapper.insertMember(newMember);

        Member findMember = memberMapper.selectMemberById(newMember.getId());

        assertThat(findMember).isNotNull();
        Member memberForTest = new Member(null, defaultBasicInfo(), defaultAdditionalInfo(), null);
        assertThat(findMember)
                .usingRecursiveComparison()
                .ignoringFields("seq", "dataHistory")
                .isEqualTo(memberForTest);
    }

    @Test
    void Select_null_by_id() {
        NewMember newMember = defaultNewMember();

        Member findMember = memberMapper.selectMemberById(newMember.getId());

        assertThat(findMember).isNull();
    }

    @Test
    void Select_a_member_by_nickname() {
        NewMember newMember = defaultNewMember();
        memberMapper.insertMember(newMember);

        Member findMember = memberMapper.selectMemberByNickname(newMember.getNickname());

        assertThat(findMember).isNotNull();
        Member memberForTest = new Member(null, defaultBasicInfo(), defaultAdditionalInfo(), null);
        assertThat(findMember)
                .usingRecursiveComparison()
                .ignoringFields("seq", "dataHistory")
                .isEqualTo(memberForTest);
    }

    @Test
    void Select_null_by_nickname() {
        NewMember newMember = defaultNewMember();

        Member findMember = memberMapper.selectMemberByNickname(newMember.getNickname());

        assertThat(findMember).isNull();
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