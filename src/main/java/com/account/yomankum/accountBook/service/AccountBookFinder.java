package com.account.yomankum.accountBook.service;

import com.account.yomankum.accountBook.domain.AccountBookRepository;
import com.account.yomankum.accountBook.dto.response.AccountBookSimpleDto;
import com.account.yomankum.common.service.SessionService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountBookFinder {

    private final AccountBookRepository accountBookRepository;
    private final SessionService sessionService;

    @Transactional(readOnly = true)
    public List<AccountBookSimpleDto> findByUser() {
        Long userId = sessionService.getSessionUserId();
        return accountBookRepository.findByCreateUser(userId.toString())
                .stream().map(AccountBookSimpleDto::from).collect(Collectors.toList());
    }

}
