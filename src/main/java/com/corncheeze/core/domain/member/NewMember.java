package com.corncheeze.core.domain.member;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class NewMember {

    private final Member.BasicInfo basicInfo;

    private final Member.AdditionalInfo additionalInfo;

    public NewMember(Member.BasicInfo basicInfo, Member.AdditionalInfo additionalInfo) {
        this.basicInfo = basicInfo;
        this.additionalInfo = additionalInfo;
    }

    public String getId() {
        return basicInfo.getId();
    }

    public String getPassword() {
        return basicInfo.getPassword();
    }

    public String getNickname() {
        return additionalInfo.getNickname();
    }
}
