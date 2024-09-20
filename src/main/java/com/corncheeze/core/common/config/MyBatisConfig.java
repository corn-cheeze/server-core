package com.corncheeze.core.common.config;

import com.corncheeze.core.adapter.out.persistence.MemberMapper;
import com.corncheeze.core.util.HostUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@MapperScan(basePackageClasses = {
        MemberMapper.class
})
public class MyBatisConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.addMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mappers/*Mapper.xml"));

        // 공통 파라미터 설정
        org.apache.ibatis.session.Configuration mybatisConf = new org.apache.ibatis.session.Configuration();
        String hostName = HostUtils.getHostName();
        mybatisConf.setVariables(new Properties() {{
            put("createUser", hostName);
            put("createApp", "core");
            put("updateUser", hostName);
            put("updateApp", "core");
        }});

        factoryBean.setConfiguration(mybatisConf);

        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory());
    }
}
