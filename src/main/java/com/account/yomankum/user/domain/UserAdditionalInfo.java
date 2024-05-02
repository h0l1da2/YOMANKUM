package com.account.yomankum.user.domain;

import com.account.yomankum.user.domain.type.Gender;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

import java.time.LocalDate;

// 통계 등을 위한 필수가 아닌 추가정보
@Getter
@Embeddable
public class UserAdditionalInfo {

    private LocalDate birthday;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String job;
    private Integer salary;

}
