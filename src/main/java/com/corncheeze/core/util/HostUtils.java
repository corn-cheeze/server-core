package com.corncheeze.core.util;

import java.net.InetAddress;

public class HostUtils {

    private static final String HOSTNAME;

    static {
        try {
            HOSTNAME = InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getHostName() {
        return HOSTNAME;
    }
}
