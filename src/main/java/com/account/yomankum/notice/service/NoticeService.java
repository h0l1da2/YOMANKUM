package com.account.yomankum.notice.service;

import com.account.yomankum.common.exception.BadRequestException;
import com.account.yomankum.common.exception.Exception;
import com.account.yomankum.notice.domain.Notice;
import com.account.yomankum.notice.domain.NoticeStatus;
import com.account.yomankum.notice.domain.UserNotice;
import com.account.yomankum.notice.repository.UserNoticeRepository;
import com.account.yomankum.user.domain.User;
import com.account.yomankum.user.service.UserFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {

    private final UserNoticeRepository userNoticeRepository;
    private final UserFinder userFinder;

    public Notice save(Long userId, String content) {
        User user = userFinder.findById(userId).orElseThrow(() -> new BadRequestException(Exception.USER_NOT_FOUND));

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
