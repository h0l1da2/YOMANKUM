package com.account.yomankum.accountBook.service;

import com.account.yomankum.accountBook.domain.AccountBookUser;
import com.account.yomankum.accountBook.domain.AccountBookUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountBookUserService {

    private final AccountBookUserRepository accountBookUserRepository;

    @Transactional
    public AccountBookUser save(AccountBookUser accountBookUser) {
        return accountBookUserRepository.save(accountBookUser);
    }
}
