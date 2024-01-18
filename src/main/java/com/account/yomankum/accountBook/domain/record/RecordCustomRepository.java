package com.account.yomankum.accountBook.domain.record;

import java.util.List;

public interface RecordCustomRepository {

    List<Record> searchRecords(Long accountBookId, RecordSearchCondition condition);

}
