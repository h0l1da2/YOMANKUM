package com.account.yomankum.domain;

import com.account.yomankum.domain.enums.Gender;
import com.account.yomankum.domain.enums.Job;
import com.account.yomankum.security.oauth.Sns;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SnsUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuidKey;
    private String email;
    private String password;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Role role;
    private String nickname;
    @Enumerated(EnumType.STRING)
    private Sns sns;

    private Date birthday;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private Job job;

    private LocalDateTime joinDate;
    private LocalDateTime pwdChangeDate;
    private LocalDateTime stopDate;
    private LocalDateTime removeDate;
    private LocalDateTime token;
}
