package com.account.yomankum.accountBook.service;

import com.account.yomankum.accountBook.domain.AccountBook;
import com.account.yomankum.accountBook.dto.response.AccountBookSimpleDto;
import com.account.yomankum.accountBook.dto.request.AccountBookWriteDto;
import com.account.yomankum.common.exception.BadRequestException;
import com.account.yomankum.common.exception.Exception;
import com.account.yomankum.common.service.SessionService;
import com.account.yomankum.accountBook.domain.AccountBookRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountBookService {

    private final AccountBookRepository accountBookRepository;
    private final SessionService sessionService;

    public Long create(AccountBookWriteDto accountBookWriteDto) {
        AccountBook accountBook = accountBookWriteDto.toEntity();
        accountBookRepository.save(accountBook);
        return accountBook.getId();
    }

    public void update(Long id, String name) {
        AccountBook accountBook = findById(id);
        accountBook.isAuthorityUser(sessionService.getSessionUserId());
        accountBook.updateName(name);
    }

    public void delete(Long id) {
        AccountBook accountBook = findById(id);
        accountBook.isAuthorityUser(sessionService.getSessionUserId());
        accountBookRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<AccountBookSimpleDto> findByUser() {
        Long userId = sessionService.getSessionUserId();
        return accountBookRepository.findByCreateUser(userId.toString())
                .stream().map(AccountBookSimpleDto::from).collect(Collectors.toList());
    }

    public AccountBook findById(Long id){
        return accountBookRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(Exception.ACCOUNT_BOOK_NOT_FOUND));
    }

}
