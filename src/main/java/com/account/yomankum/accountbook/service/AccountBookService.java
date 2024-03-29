package com.account.yomankum.accountBook.service;

import com.account.yomankum.accountBook.domain.AccountBook;
import com.account.yomankum.accountBook.dto.request.AccountBookCreateRequest;
import com.account.yomankum.common.exception.BadRequestException;
import com.account.yomankum.common.exception.Exception;
import com.account.yomankum.common.service.SessionService;
import com.account.yomankum.accountBook.domain.AccountBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountBookService {

    private final AccountBookRepository accountBookRepository;
    private final AccountBookFinder accountBookFinder;
    private final SessionService sessionService;

    public Long create(AccountBookCreateRequest accountBookWriteDto) {
        AccountBook accountBook = accountBookWriteDto.toEntity();
        accountBookRepository.save(accountBook);
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
