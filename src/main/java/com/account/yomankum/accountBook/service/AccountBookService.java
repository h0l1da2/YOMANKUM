package com.account.yomankum.accountBook.service;

import com.account.yomankum.accountBook.domain.*;
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

@Service
@Transactional
@RequiredArgsConstructor
public class AccountBookService {

    private final AccountBookRepository accountBookRepository;
    private final AccountBookFinder accountBookFinder;
    private final NoticeService noticeService;
    private final UserFinder userFinder;

    public Long create(AccountBookCreateRequest accountBookWriteDto, Long requesterId) {
        AccountBook accountBook = accountBookWriteDto.toAccountBookEntity();
        User user = userFinder.findById(requesterId).orElseThrow(() -> new BadRequestException(Exception.USER_NOT_FOUND));
        accountBook.addAccountBookUser(user, AccountBookRole.OWNER, AccountBookUserStatus.PARTICIPATING);
        accountBookRepository.save(accountBook);
        return accountBook.getId();
    }

    public void update(Long accountBookId, String name, Long requesterId) {
        AccountBook accountBook = accountBookFinder.findById(accountBookId);
        accountBook.updateName(name, requesterId);
    }

    public void delete(Long accountBookId, Long requesterId) {
        AccountBook accountBook = accountBookFinder.findById(accountBookId);
        accountBook.checkHasOwnerAuth(requesterId);
        accountBookRepository.delete(accountBook);
    }

    public void invite(Long accountBookId, AccountBookInviteRequest accountBookInviteRequest, Long requesterId) {
        AccountBook accountBook = accountBookFinder.findById(accountBookId);
        accountBook.checkHasOwnerAuth(requesterId);
        User user = userFinder.findByEmail(accountBookInviteRequest.email()).orElseThrow(() -> new BadRequestException(Exception.USER_NOT_FOUND));
        accountBook.addAccountBookUser(user, AccountBookRole.GENERAL, AccountBookUserStatus.INVITING);

        noticeService.save(user.getId(), accountBook.getName() + "에 초대되셨습니다.");
        // SSE 또는 소켓 추가 필요..
    }

    public void removeAccountBookUser(Long accountBookId, Long userId, Long requesterId) {
        AccountBook accountBook = accountBookFinder.findById(accountBookId);
        accountBook.removeUser(userId, requesterId);
    }

}
