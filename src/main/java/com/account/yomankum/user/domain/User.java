package com.account.yomankum.user.domain;

import com.account.yomankum.user.dto.request.UserInfoUpdateRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private String picture;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @Enumerated(EnumType.STRING)
    private AuthInfo authInfo;

    private LocalDate birthday;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String job;
    private Integer salary;

//    @Embedded
//    private UserAdditionalInfo additionalInfo;

    private LocalDateTime joinDatetime;
    private LocalDateTime stopDatetime;
    private LocalDateTime pwdChangeDatetime;
    private LocalDateTime removeDatetime;


    public void updateUserInfo(UserInfoUpdateRequest request) {
        this.nickname = request.nickname();
        this.gender = request.gender();
        this.birthday = request.birthDate();
        this.job = request.job();
        this.salary = request.salary();
    }

    public String getRefreshToken(){
        return authInfo.getRefreshToken();
    }

    public void encryptPassword(PasswordEncoder encoder) {
        this.password = encoder.encode(password);
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeRefreshToken(String token) {
        this.authInfo.setRefreshToken(token);
    }

    public String getOauthId() {
        return authInfo.getOauthId();
    }
}
