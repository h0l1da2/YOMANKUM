package com.account.yomankum.user.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Job {
    EX("기타");

    private final String jobName;
}
