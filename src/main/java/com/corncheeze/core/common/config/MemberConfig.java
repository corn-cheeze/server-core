package com.corncheeze.core.common.config;

import com.corncheeze.core.adapter.out.persistence.MemberMapper;
import com.corncheeze.core.adapter.out.persistence.MemberRepositoryMyBatis;
import com.corncheeze.core.application.port.out.MemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemberConfig {

    @Bean
    public MemberRepository memberRepository(MemberMapper urlMapper) {
        return new MemberRepositoryMyBatis(urlMapper);
    }
}
