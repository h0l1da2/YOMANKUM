package com.account.yomankum.notice.repository;

import com.account.yomankum.notice.domain.UserNotice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserNoticeRepository extends JpaRepository<UserNotice, Long> {
}
