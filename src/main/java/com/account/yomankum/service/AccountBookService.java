package com.account.yomankum.service;

import com.account.yomankum.domain.dto.AccountWriteDto;

import java.util.List;

public interface AccountBookService {
    void write(List<AccountWriteDto> accountWriteDtoList);
}
