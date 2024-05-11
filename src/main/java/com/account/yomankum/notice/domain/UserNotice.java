package com.account.yomankum.notice.domain;

import com.account.yomankum.user.domain.User;
import jakarta.persistence.*;
import jdk.jfr.Name;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserNotice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Name("userNotice_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id")
    private Notice notice;
}
