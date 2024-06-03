package com.account.yomankum.accountBook.service;

import com.account.yomankum.accountBook.domain.*;
import com.account.yomankum.accountBook.domain.tag.DefaultTag;
import com.account.yomankum.accountBook.domain.tag.Tag;
import com.account.yomankum.accountBook.dto.request.AccountBookCreateRequest;
import com.account.yomankum.accountBook.dto.request.AccountBookInviteRequest;
import com.account.yomankum.common.exception.BadRequestException;
import com.account.yomankum.common.exception.Exception;
import com.account.yomankum.notice.service.NoticeService;
import com.account.yomankum.user.domain.User;
import com.account.yomankum.user.service.UserFinder;
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
    private final NoticeService noticeService;
    private final UserFinder userFinder;

    public Long create(AccountBookCreateRequest accountBookWriteDto, Long requesterId) {
        User user = userFinder.findById(requesterId).orElseThrow(() -> new BadRequestException(Exception.USER_NOT_FOUND));

        AccountBook accountBook = accountBookWriteDto.toAccountBookEntity();
        addNewUser(accountBook, user);
        accountBookRepository.save(accountBook);

        List<Tag> defaultTags = DefaultTag.getDefaultMainTags();
        accountBook.addTags(defaultTags, requesterId);

        return accountBook.getId();
    }

    public void update(Long accountBookId, String name, Long requesterId) {
        AccountBook accountBook = accountBookFinder.findById(accountBookId);
        accountBook.updateName(name, requesterId);
    }

    public void delete(Long accountBookId, Long requesterId) {
        AccountBook accountBook = accountBookFinder.findById(accountBookId);
        AccountBookRole accountBookRole = accountBook.getAccountBookRole(requesterId);

        if (accountBookRole.equals(AccountBookRole.OWNER)) {
            accountBookRepository.deleteById(accountBookId);
        } else {
            accountBook.delete(requesterId);
            accountBookUserRepository.deleteByUserId(requesterId);
        }
    }

    // TODO 공유 가계부 초대 개발
    public void invite(Long accountBookId, AccountBookInviteRequest accountBookInviteRequest, Long requesterId) {
        AccountBook accountBook = accountBookFinder.findById(accountBookId);
        accountBook.checkAuthorizedUser(requesterId);

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
