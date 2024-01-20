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
    private final SessionService sessionService;

    public Long create(AccountBookCreateRequest accountBookWriteDto) {
        AccountBook accountBook = accountBookWriteDto.toEntity();
        accountBookRepository.save(accountBook);
        return accountBook.getId();
    }

    public void update(Long id, String name) {
        AccountBook accountBook = findById(id);
        accountBook.updateName(name, sessionService.getSessionUserId());
    }

    public void delete(Long id) {
        AccountBook accountBook = findById(id);
        accountBook.delete(sessionService.getSessionUserId());
        accountBookRepository.deleteById(id);
    }

    public AccountBook findById(Long id){
        return accountBookRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(Exception.ACCOUNT_BOOK_NOT_FOUND));
    }

}
