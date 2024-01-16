package com.account.yomankum.accountBook.controller;

import com.account.yomankum.accountBook.domain.record.Record;
import com.account.yomankum.accountBook.domain.record.RecordSearchCondition;
import com.account.yomankum.accountBook.dto.request.RecordWriteDto;
import com.account.yomankum.accountBook.service.RecordFinder;
import com.account.yomankum.accountBook.service.RecordService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/record")
public class RecordController {

    private final RecordFinder recordFinder;
    private final RecordService recordService;

    @GetMapping("/{accountBookId}")
    public List<Record> findByAccountBook(@PathVariable Long accountBookId, RecordSearchCondition condition){
        return recordFinder.findBy(accountBookId, condition);
    }

    @PostMapping("/{accountBookId}")
    public void addRecord(@PathVariable Long accountBookId, @RequestBody RecordWriteDto recordWriteDto){
        recordService.addRecord(accountBookId, recordWriteDto);
    }

}
