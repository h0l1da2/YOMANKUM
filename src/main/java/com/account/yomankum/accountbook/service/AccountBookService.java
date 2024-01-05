package com.account.yomankum.accountbook.service;

import com.account.yomankum.accountbook.domain.AccountWriteDto;

import java.util.List;

public interface AccountBookService {
    void write(List<AccountWriteDto> accountWriteDtoList);
}
