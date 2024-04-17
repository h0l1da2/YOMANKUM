package com.account.yomankum.accountBook.service;

import com.account.yomankum.accountBook.domain.AccountBook;
import com.account.yomankum.accountBook.domain.AccountBookRepository;
import com.account.yomankum.accountBook.domain.AccountBookUser;
import com.account.yomankum.accountBook.domain.AccountBookUserRepository;
import com.account.yomankum.accountBook.domain.tag.DefaultTag;
import com.account.yomankum.accountBook.domain.tag.Tag;
import com.account.yomankum.accountBook.dto.request.AccountBookCreateRequest;
import com.account.yomankum.common.exception.BadRequestException;
import com.account.yomankum.common.exception.Exception;
import com.account.yomankum.common.service.SessionService;
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
    private final AccountBookUserRepository accountBookUserRepository;
    private final UserRepository userRepository;
    private final SessionService sessionService;

    public Long create(AccountBookCreateRequest accountBookWriteDto) {
        AccountBook accountBook = accountBookWriteDto.toAccountBookEntity();
        accountBookRepository.save(accountBook);
        List<Tag> defaultTags = DefaultTag.getDefaultMainTags();
        Long sessionUserId = sessionService.getSessionUserId();
        accountBook.addTags(defaultTags, sessionUserId);

        User user = userRepository.findById(sessionUserId)
                .orElseThrow(() -> new BadRequestException(Exception.USER_NOT_FOUND));
        AccountBookUser accountBookUser =
                accountBookUserRepository.save(accountBookWriteDto.toAccountBookUserEntity(accountBook, user));
        user.addAccountBook(accountBookUser, sessionUserId);
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

}
