package com.account.yomankum.domain;

import jakarta.persistence.*;
import lombok.*;

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
    private String username;
    private String password;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Role role;
    private LocalDateTime pwdChangeDate;
    private LocalDateTime stopDate;
    private LocalDateTime removeDate;
}
