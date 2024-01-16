package com.account.yomankum.accountBook.service;

import com.account.yomankum.accountBook.domain.AccountBook;
import com.account.yomankum.accountBook.domain.record.RecordRepository;
import com.account.yomankum.accountBook.dto.request.RecordWriteDto;
import com.account.yomankum.common.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RecordService {

    private final RecordRepository recordRepository;
    private final AccountBookService accountBookService;
    private final SessionService sessionService;

    public void addRecord(Long accountBookId, RecordWriteDto dto){
        AccountBook accountBook = accountBookService.findById(accountBookId);
        accountBook.isAuthorityUser(sessionService.getSessionUserId());
        accountBook.addRecord(dto.toEntity());
    }

}
