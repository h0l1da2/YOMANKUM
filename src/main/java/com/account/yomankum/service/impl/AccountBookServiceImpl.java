package com.account.yomankum.service.impl;

import com.account.yomankum.domain.AccountBook;
import com.account.yomankum.domain.dto.AccountWriteDto;
import com.account.yomankum.repository.AccountBookRepository;
import com.account.yomankum.service.AccountBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountBookServiceImpl implements AccountBookService {

    private final AccountBookRepository accountBookRepository;

    @Override
    public void write(List<AccountWriteDto> accountWriteDtoList) {

        accountWriteDtoList.stream().forEach(accountWriteDto -> {

            AccountBook accountBook = AccountBook.builder()
                    .date(accountWriteDto.getDate())
                    .money(accountWriteDto.getMoney())
                    .tag(accountWriteDto.getTag())
                    .usage(accountWriteDto.getUsage())
                    .payment(accountWriteDto.getPayment())
                    .customTag(accountWriteDto.getCustomTag())
                    .build();

            accountBookRepository.save(accountBook);

        });

    }
}
