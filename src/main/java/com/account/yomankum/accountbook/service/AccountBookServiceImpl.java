package com.account.yomankum.accountbook.service;

import com.account.yomankum.domain.AccountBook;
import com.account.yomankum.accountbook.domain.AccountWriteDto;
import com.account.yomankum.repository.AccountBookRepository;
import com.account.yomankum.accountbook.service.AccountBookService;
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
                    .content(accountWriteDto.getUsage())
                    .payment(accountWriteDto.getPayment())
                    .customTag(accountWriteDto.getCustomTag())
                    .build();

            accountBookRepository.save(accountBook);

        });

    }
}
