package com.account.yomankum.user.dto.response;

import com.account.yomankum.common.util.DatetimeConverter;
import com.account.yomankum.user.domain.User;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserInfoDto(
        String email,
        String nickname,
        LocalDate joinDate,
        LocalDate pwdChangeDate,
        String job,
        Integer salary
) {
    public static UserInfoDto from(User user) {
        return UserInfoDto.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .joinDate(DatetimeConverter.instantToLocalDate(user.getJoinDatetime()))
                .pwdChangeDate(DatetimeConverter.instantToLocalDate(user.getPwdChangeDatetime()))
                .job(user.getJob())
                .salary(user.getSalary())
                .build();
    }
}
