package com.account.yomankum.accountBook.service;

import com.account.yomankum.accountBook.domain.record.Record;
import com.account.yomankum.accountBook.domain.record.RecordRepository;
import com.account.yomankum.accountBook.domain.record.RecordSearchCondition;
import com.account.yomankum.common.service.SessionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecordFinder {

    private final RecordRepository repository;
    private final SessionService sessionService;

    public List<Record> searchRecords(Long accountBookId, RecordSearchCondition condition) {
        return repository.searchRecords(accountBookId, sessionService.getSessionUserId(), condition);
    }

}
