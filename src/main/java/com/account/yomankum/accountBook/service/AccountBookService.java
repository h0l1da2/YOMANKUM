package com.account.yomankum.accountBook.service;

import com.account.yomankum.accountBook.domain.*;
import com.account.yomankum.accountBook.domain.tag.DefaultTag;
import com.account.yomankum.accountBook.domain.tag.Tag;
import com.account.yomankum.accountBook.dto.request.AccountBookCreateRequest;
import com.account.yomankum.accountBook.dto.request.AccountBookInviteRequest;
import com.account.yomankum.common.exception.BadRequestException;
import com.account.yomankum.common.exception.Exception;
import com.account.yomankum.common.service.SessionService;
import com.account.yomankum.notice.service.NoticeService;
import com.account.yomankum.user.domain.User;
import com.account.yomankum.user.service.UserFinder;
import com.account.yomankum.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountBookService {

    private final AccountBookRepository accountBookRepository;
    private final AccountBookUserRepository accountBookUserRepository;
    private final AccountBookFinder accountBookFinder;
    private final SessionService sessionService;
    private final NoticeService noticeService;
    private final UserFinder userFinder;

    public Long create(AccountBookCreateRequest accountBookWriteDto) {
        // 가계부유저 추가
        Long sessionUserId = sessionService.getSessionUserId();
        User user = userFinder.findById(sessionUserId).orElseThrow(() -> new BadRequestException(Exception.USER_NOT_FOUND));

        AccountBook accountBook = accountBookWriteDto.toAccountBookEntity();
        addNewUser(accountBook, user);
        accountBookRepository.save(accountBook);

        List<Tag> defaultTags = DefaultTag.getDefaultMainTags();
        accountBook.addTags(defaultTags, sessionUserId);

        return accountBook.getId();
    }

    public void update(Long id, String name) {
        AccountBook accountBook = accountBookFinder.findById(id);
        accountBook.updateName(name, sessionService.getSessionUserId());
    }

    public void delete(Long id) {
        AccountBook accountBook = accountBookFinder.findById(id);
        Long sessionUserId = sessionService.getSessionUserId();
        AccountBookRole accountBookRole = accountBook.getAccountBookRole(sessionUserId);

        if (accountBookRole.equals(AccountBookRole.OWNER)) {
            accountBookRepository.deleteById(id);
        } else {
            accountBook.delete(sessionUserId);
            accountBookUserRepository.deleteByUserId(sessionUserId);
        }
    }

    public void invite(Long id, AccountBookInviteRequest accountBookInviteRequest) {
        AccountBook accountBook = accountBookFinder.findById(id);
        accountBook.checkAuthorizedUser(sessionService.getSessionUserId());

        User user = userFinder.findByEmail(accountBookInviteRequest.email()).orElseThrow(() -> new BadRequestException(Exception.USER_NOT_FOUND));

        addNewUser(accountBook, user);

        noticeService.save(user.getId(), accountBook.getName() + "에 초대되셨습니다.");
        // SSE 또는 소켓 추가 필요..
    }

    public void addNewUser(AccountBook accountBook, User user) {
        AccountBookRole role = accountBook.getCreateUserId() == null ?
                AccountBookRole.OWNER : AccountBookRole.READ_ONLY;

        UserStatus userStatus = role == AccountBookRole.OWNER ?
                UserStatus.PARTICIPATING : UserStatus.INVITING;

        AccountBookUser accountBookUser = AccountBookUser.builder()
                .accountBook(accountBook)
                .user(user)
                .nickname(user.getNickname())
                .accountBookRole(role)
                .status(userStatus)
                .build();

        accountBook.addAccountBookUser(accountBookUser);
        user.addAccountBook(accountBookUser);
    }

}
