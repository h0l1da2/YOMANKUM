package com.account.yomankum.notice.domain;

import jakarta.persistence.*;
import jdk.jfr.Name;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Name("notice_id")
    private Long id;
    private String content;
    @Enumerated(EnumType.STRING)
    private NoticeStatus noticeStatus;
}
