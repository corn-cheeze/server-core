package com.corncheeze.core.domain.member;

import com.corncheeze.core.domain.datahistory.DataHistory;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class Member {

    private final Long seq;

    private final BasicInfo basicInfo;

    private final AdditionalInfo additionalInfo;

    private final DataHistory dataHistory;

    public Member(Long seq, BasicInfo basicInfo, AdditionalInfo additionalInfo, DataHistory dataHistory) {
        this.seq = seq;
        this.basicInfo = basicInfo;
        this.additionalInfo = additionalInfo;
        this.dataHistory = dataHistory;
    }

    @ToString
    @EqualsAndHashCode
    public static class BasicInfo {

        private final String id;

        private final String password;

        public BasicInfo(String id, String password) {
            this.id = id;
            this.password = password;
        }

        public String getId() {
            return id;
        }

        public String getPassword() {
            return password;
        }
    }

    @ToString
    @EqualsAndHashCode
    public static class AdditionalInfo {

        private final String nickname;

        public AdditionalInfo(String nickname) {
            this.nickname = nickname;
        }

        public String getNickname() {
            return nickname;
        }
    }

    public String getId() {
        return basicInfo.getId();
    }

    public String getNickname() {
        return additionalInfo.getNickname();
    }
}
