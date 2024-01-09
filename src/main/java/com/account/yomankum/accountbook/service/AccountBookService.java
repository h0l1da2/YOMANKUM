package com.account.yomankum.accountBook.service;

import com.account.yomankum.accountBook.domain.AccountBook;
import com.account.yomankum.accountBook.service.dto.read.AccountBookSimpleDto;
import com.account.yomankum.accountBook.service.dto.write.AccountBookWriteDto;
import java.util.List;

public interface AccountBookService {
    Long create(AccountBookWriteDto accountBookWriteDto);
    void update(Long id, String name);
    void delete(Long id);
    List<AccountBookSimpleDto> findByUser();
}
