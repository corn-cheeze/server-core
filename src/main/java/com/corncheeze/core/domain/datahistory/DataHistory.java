package com.corncheeze.core.domain.datahistory;

import java.time.LocalDateTime;

public class DataHistory {

    private final String createUser;

    private final String createApp;

    private final LocalDateTime createDt;

    private final String updateUser;

    private final String updateApp;

    private final LocalDateTime updateDt;

    public DataHistory(String createUser, String createApp, LocalDateTime createDt, String updateUser, String updateApp, LocalDateTime updateDt) {
        this.createUser = createUser;
        this.createApp = createApp;
        this.createDt = createDt;
        this.updateUser = updateUser;
        this.updateApp = updateApp;
        this.updateDt = updateDt;
    }

    @Override
    public String toString() {
        return "DataHistory{" +
                "createUser='" + createUser + '\'' +
                ", createApp='" + createApp + '\'' +
                ", createDt=" + createDt +
                ", updateUser='" + updateUser + '\'' +
                ", updateApp='" + updateApp + '\'' +
                ", updateDt=" + updateDt +
                '}';
    }
}
