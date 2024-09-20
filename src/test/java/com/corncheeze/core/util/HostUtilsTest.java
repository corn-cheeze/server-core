package com.corncheeze.core.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

class HostUtilsTest {

    @Test
    void getHostName() throws UnknownHostException {
        String hostName = InetAddress.getLocalHost().getHostName();

        String getHostName = HostUtils.getHostName();

        Assertions.assertThat(getHostName).isEqualTo(hostName);
    }
}