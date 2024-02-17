package com.account.yomankum.accountBook.domain.record.repository;

import com.account.yomankum.accountBook.domain.record.Record;
import com.account.yomankum.accountBook.domain.record.RecordSearchCondition;
import java.util.List;

public interface RecordCustomRepository {

    List<Record> searchRecords(Long accountBookId, RecordSearchCondition condition);

}
