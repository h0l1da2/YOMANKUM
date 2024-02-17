package com.account.yomankum.accountBook.service;

import com.account.yomankum.accountBook.domain.AccountBook;
import com.account.yomankum.accountBook.domain.record.Record;
import com.account.yomankum.accountBook.domain.record.RecordSearchCondition;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagFinder {

    private final RecordFinder recordFinder;

    public List<String> findMajorTagsByAccountBook(Long accountBookId, LocalDate from, LocalDate to){
        List<Record> records = recordFinder.searchRecords(accountBookId, RecordSearchCondition.of(from, to));
        return records.stream().map(Record::getMajorTag).collect(Collectors.toList());
    }

}
