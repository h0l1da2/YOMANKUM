package com.account.yomankum.accountBook.service;

import com.account.yomankum.accountBook.domain.AccountBook;
import com.account.yomankum.accountBook.domain.record.Record;
import com.account.yomankum.accountBook.domain.record.RecordRepository;
import com.account.yomankum.accountBook.domain.tag.Tag;
import com.account.yomankum.accountBook.dto.request.RecordCreateRequest;
import com.account.yomankum.accountBook.dto.request.RecordUpdateRequest;
import com.account.yomankum.common.exception.BadRequestException;
import com.account.yomankum.common.exception.Exception;
import com.account.yomankum.common.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RecordService {

    private final RecordRepository recordRepository;
    private final AccountBookFinder accountBookFinder;
    private final SessionService sessionService;
    private final MainTagFinder mainTagFinder;

    public void addRecord(Long accountBookId, RecordCreateRequest dto){
        Tag mainTag = mainTagFinder.findById(dto.mainTagId());
        Record record = dto.toEntity(mainTag);
        AccountBook accountBook = accountBookFinder.findById(accountBookId);
        accountBook.addRecord(record, sessionService.getSessionUserId());
        recordRepository.save(record);
    }

    public void updateRecord(Long recordId, RecordUpdateRequest request) {
        Record record = recordRepository.findById(recordId).orElseThrow(()->new BadRequestException(Exception.RECORD_BOOK_NOT_FOUND));
        Tag tag = mainTagFinder.findById(request.mainTagId());
        record.update(request, tag, sessionService.getSessionUserId());
    }

    public void deleteRecord(Long recordId){
        Record record = recordRepository.findById(recordId).orElseThrow(()->new BadRequestException(Exception.RECORD_BOOK_NOT_FOUND));
        record.delete(sessionService.getSessionUserId());
    }
}
