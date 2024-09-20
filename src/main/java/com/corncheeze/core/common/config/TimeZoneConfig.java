package com.corncheeze.core.common.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.TimeZone;

@Component
public class TimeZoneConfig implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {
        // 기본 JVM 타임존을 설정합니다.
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}
