package com.corncheeze.core.adapter.out.persistence;

import com.corncheeze.core.application.port.out.MemberRepository;
import com.corncheeze.core.common.config.MemberConfig;
import com.corncheeze.core.common.config.MyBatisConfig;
import com.corncheeze.core.domain.member.Member;
import com.corncheeze.core.domain.member.NewMember;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {MyBatisConfig.class, MemberConfig.class})
class MemberRepositoryMyBatisMockTest {

    @MockBean
    private MemberMapper mockMemberMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void Saved_member_must_be_queried() {
        NewMember newMember = defaultNewMember();
        Mockito.when(mockMemberMapper.selectMemberById(defaultId())).thenReturn(null);

        assertThatThrownBy(() -> memberRepository.save(newMember))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Didn't find member with NewMember: " + newMember);
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