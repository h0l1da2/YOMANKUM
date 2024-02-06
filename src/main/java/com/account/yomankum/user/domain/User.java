package com.account.yomankum.user.domain;

import com.account.yomankum.user.domain.type.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

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

    private Date birthday;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String job;
    private Integer salary;

    private LocalDateTime joinDate;
    private LocalDateTime pwdChangeDate;
    private LocalDateTime stopDate;
    private LocalDateTime removeDate;

    public void updatePassword(String password) {
        this.password = password;
    }
}
