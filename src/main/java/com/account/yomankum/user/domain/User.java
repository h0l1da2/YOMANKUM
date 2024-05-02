package com.account.yomankum.user.domain;

import com.account.yomankum.user.domain.type.Gender;
import com.account.yomankum.user.dto.request.UserInfoUpdateRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@Table(name = "USERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Role role;
    private String nickname;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @Enumerated(EnumType.STRING)
    private AuthType authType;

    private LocalDate birthday;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String job;
    private Integer salary;

//    @Embedded
//    private UserAdditionalInfo additionalInfo;

    private LocalDateTime joinDatetime;
    private LocalDateTime lastLoginDatetime;
    private LocalDateTime stopDatetime;
    private LocalDateTime pwdChangeDatetime;
    private LocalDateTime removeDatetime;


    public void updatePassword(PasswordEncoder passwordEncoder, String password) {
        this.password = passwordEncoder.encode(password);
    }

    public void updateLastLoginDatetime() {
        this.lastLoginDatetime = LocalDateTime.now();
    }

    public void updateUserInfo(UserInfoUpdateRequest request) {
        this.gender = request.gender();
        this.birthday = request.birthDate();
        this.job = request.job();
        this.salary = request.salary();
    }
}
