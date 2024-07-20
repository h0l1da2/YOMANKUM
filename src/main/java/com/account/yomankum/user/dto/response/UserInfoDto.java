package com.account.yomankum.user.dto.response;

import com.account.yomankum.user.domain.User;
import com.account.yomankum.user.domain.UserType;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserInfoDto(
        String email,
        String nickname,
        LocalDate joinDate,
        LocalDate pwdChangeDate,
        UserType userType,
        String job,
        Integer salary
) {
    public static UserInfoDto from(User user) {
        return UserInfoDto.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .joinDate(user.getJoinDatetime().toLocalDate())
                .pwdChangeDate(user.getPwdChangeDatetime().toLocalDate())
                .job(user.getJob())
                .salary(user.getSalary())
                .userType(user.getUserType())
                .build();
    }
}
