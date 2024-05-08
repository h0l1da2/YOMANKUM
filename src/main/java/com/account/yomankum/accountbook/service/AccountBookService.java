package com.account.yomankum.accountBook.service;

import com.account.yomankum.accountBook.domain.*;
import com.account.yomankum.accountBook.domain.tag.DefaultTag;
import com.account.yomankum.accountBook.domain.tag.Tag;
import com.account.yomankum.accountBook.dto.request.AccountBookCreateRequest;
import com.account.yomankum.accountBook.dto.request.AccountBookInviteRequest;
import com.account.yomankum.common.service.SessionService;
import com.account.yomankum.notice.service.NoticeService;
import com.account.yomankum.user.domain.User;
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
    private final AccountBookFinder accountBookFinder;
    private final AccountBookUserService accountBookUserService;
    private final UserService userService;
    private final SessionService sessionService;
    private final NoticeService noticeService;

    public Long create(AccountBookCreateRequest accountBookWriteDto) {
        // 가계부유저 추가
        Long sessionUserId = sessionService.getSessionUserId();
        User user = userService.findById(sessionUserId);

        AccountBook accountBook = accountBookWriteDto.toAccountBookEntity();
        addNewUser(accountBook, user, accountBookWriteDto.role());
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
        accountBook.delete(sessionService.getSessionUserId());
        accountBookRepository.deleteById(id);
    }

    // TODO 공유 가계부 초대 개발
    public void invite(Long id, AccountBookInviteRequest accountBookInviteRequest) {
        AccountBook accountBook = accountBookFinder.findById(id);
        accountBook.checkAuthorizedUser(sessionService.getSessionUserId());

        User user = userService.findByEmail(accountBookInviteRequest.email());

        AccountBookUser accountBookUser = accountBookUserService.save(
                AccountBookUser.builder()
                        .nickname(user.getNickname())
                        .accountBookRole(AccountBookRole.READ_ONLY)
                        .status(UserStatus.INVITING)
                        .accountBook(accountBook)
                        .build());

        user.addAccountBook(accountBookUser);
        accountBook.addAccountBookUser(accountBookUser);
        // 알림 메시지
        noticeService.save(user, accountBook.getName() + "에 초대되셨습니다.");
    }

    public void addNewUser(AccountBook accountBook, User user, AccountBookRole role) {
        AccountBookUser accountBookUser = AccountBookUser.builder()
                .accountBook(accountBook)
                .user(user)
                .nickname(user.getNickname())
                .accountBookRole(role)
                .status(UserStatus.PARTICIPATING)
                .build();

        accountBook.addAccountBookUser(accountBookUser);
        user.addAccountBook(accountBookUser);
    }

}
