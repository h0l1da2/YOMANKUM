package com.account.yomankum.notice.service;

import com.account.yomankum.notice.domain.Notice;
import com.account.yomankum.notice.domain.NoticeStatus;
import com.account.yomankum.notice.domain.UserNotice;
import com.account.yomankum.notice.repository.UserNoticeRepository;
import com.account.yomankum.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {

    private final UserNoticeRepository userNoticeRepository;

    public Notice save(User user, String content) {
        Notice notice = Notice.builder()
                .noticeStatus(NoticeStatus.UN_READ)
                .content(content)
                .build();

        UserNotice userNotice = UserNotice.builder()
                .user(user)
                .notice(notice)
                .build();

        userNoticeRepository.save(userNotice);
        return notice;
    }
}
