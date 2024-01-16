package com.account.yomankum.accountBook.service;

import com.account.yomankum.accountBook.domain.record.Record;
import com.account.yomankum.accountBook.domain.record.RecordRepository;
import com.account.yomankum.accountBook.domain.record.RecordSearchCondition;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecordFinder {

    private final RecordRepository repository;

    public List<Record> findBy(Long accountBookId, RecordSearchCondition condition) {
        return null;
    }

}
