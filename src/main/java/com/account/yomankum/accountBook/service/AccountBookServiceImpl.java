package com.account.yomankum.accountBook.service;

import com.account.yomankum.accountBook.domain.AccountBook;
import com.account.yomankum.accountBook.service.dto.read.AccountBookSimpleDto;
import com.account.yomankum.accountBook.service.dto.write.AccountBookWriteDto;
import com.account.yomankum.common.service.SessionService;
import com.account.yomankum.repository.AccountBookRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountBookServiceImpl implements AccountBookService {

    private final AccountBookRepository accountBookRepository;
    private final SessionService sessionService;

    @Override
    public Long create(AccountBookWriteDto accountBookWriteDto) {
        AccountBook accountBook = accountBookWriteDto.toEntity();
        accountBookRepository.save(accountBook);
        return accountBook.getId();
    }

    @Override
    public void update(Long id, String name) {
        AccountBook accountBook = findById(id);
        accountBook.updateName(sessionService.getSessionUserId(), name);
    }

    @Override
    public void delete(Long id) {
        AccountBook accountBook = findById(id);
        accountBook.delete(sessionService.getSessionUserId());
        accountBookRepository.deleteById(id);
    }

    @Override
    public List<AccountBookSimpleDto> findByUser() {
        Long userId = sessionService.getSessionUserId();
        return accountBookRepository.findByCreateUser(userId.toString())
                .stream().map(AccountBookSimpleDto::from).collect(Collectors.toList());
    }

    private AccountBook findById(Long id){
        return accountBookRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("account book not found... id : " + id));
    }

}
