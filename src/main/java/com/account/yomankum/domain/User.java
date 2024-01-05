package com.account.yomankum.domain;

import com.account.yomankum.domain.enums.Gender;
import com.account.yomankum.domain.enums.Job;
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
    @Enumerated(EnumType.STRING)
    private Job job;

    private LocalDateTime joinDate;
    private LocalDateTime pwdChangeDate;
    private LocalDateTime stopDate;
    private LocalDateTime removeDate;

}
