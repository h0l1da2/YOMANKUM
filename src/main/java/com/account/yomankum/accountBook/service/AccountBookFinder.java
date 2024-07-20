package com.account.yomankum.accountBook.service;

import com.account.yomankum.accountBook.domain.AccountBook;
import com.account.yomankum.accountBook.domain.AccountBookRepository;
import com.account.yomankum.accountBook.dto.response.AccountBookSimpleDto;
import com.account.yomankum.common.exception.BadRequestException;
import com.account.yomankum.common.exception.Exception;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountBookFinder {

    private final AccountBookRepository accountBookRepository;

    public List<AccountBookSimpleDto> findByUser(Long userId) {
        return accountBookRepository.findByCreateUser(userId.toString())
                .stream().map(AccountBookSimpleDto::from).collect(Collectors.toList());
    }

    public AccountBook findById(Long accountBookId){
        return accountBookRepository.findById(accountBookId)
                .orElseThrow(() -> new BadRequestException(Exception.ACCOUNT_BOOK_NOT_FOUND));
    }

}
