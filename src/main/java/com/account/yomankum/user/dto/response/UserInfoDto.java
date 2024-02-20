package com.account.yomankum.user.dto.response;

import com.account.yomankum.user.domain.User;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserInfoDto(
        String email,
        String nickname,
        LocalDateTime joinDate,
        LocalDateTime pwdChangeDate,
        String job,
        Integer salary
) {
    public static UserInfoDto from(User user) {
        return UserInfoDto.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .joinDate(user.getJoinDatetime())
                .pwdChangeDate(user.getPwdChangeDatetime())
                .job(user.getJob())
                .salary(user.getSalary())
                .build();
    }
}
