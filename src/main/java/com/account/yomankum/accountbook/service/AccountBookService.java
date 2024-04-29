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
import com.account.yomankum.user.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final SessionService sessionService;
    private final NoticeService noticeService;


    public Long create(AccountBookCreateRequest accountBookWriteDto) {
        AccountBook accountBook = accountBookWriteDto.toAccountBookEntity();
        accountBookRepository.save(accountBook);
        List<Tag> defaultTags = DefaultTag.getDefaultMainTags();
        Long sessionUserId = sessionService.getSessionUserId();
        accountBook.addTags(defaultTags, sessionUserId);

        // FIXME 나중에 UseFinder 에서 findById 하게 수정필요
        User user = userRepository.findById(sessionUserId)
                .orElseThrow(() -> new BadRequestException(Exception.USER_NOT_FOUND));
        AccountBookUser accountBookUser =
                accountBookUserService.save(accountBookWriteDto.toAccountBookUserEntity(accountBook, user));
        user.addAccountBook(accountBookUser);
        accountBook.addAccountBookUser(accountBookUser);
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

    public void invite(Long id, AccountBookInviteRequest accountBookInviteRequest) {
        AccountBook accountBook = accountBookFinder.findById(id);
        accountBook.checkAuthorizedUser(sessionService.getSessionUserId());

        User user = userRepository.findByEmail(accountBookInviteRequest.email())
                .orElseThrow(() -> new BadRequestException(Exception.USER_NOT_FOUND));

        AccountBookUser accountBookUser = accountBookUserService.save(
                AccountBookUser.builder()
                        .nickname(user.getNickname())
                        .accountBookRole(AccountBookRole.READ_ONLY)
                        .status(UserStatus.INVITING)
                        .accountBook(accountBook)
                        .build()
        );

        user.addAccountBook(accountBookUser);
        accountBook.addAccountBookUser(accountBookUser);
        // 알림 메시지
        noticeService.save(user, accountBook.getName() + "에 초대되셨습니다.");
    }
}
