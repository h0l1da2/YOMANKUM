package com.account.yomankum.accountBook.controller;

import com.account.yomankum.accountBook.domain.record.Record;
import com.account.yomankum.accountBook.domain.record.RecordSearchCondition;
import com.account.yomankum.accountBook.dto.request.RecordCreateRequest;
import com.account.yomankum.accountBook.dto.request.RecordUpdateRequest;
import com.account.yomankum.accountBook.service.RecordFinder;
import com.account.yomankum.accountBook.service.RecordService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public List<Record> searchRecords(@PathVariable Long accountBookId, RecordSearchCondition condition){
        return recordFinder.searchRecords(accountBookId, condition);
    }

    @PostMapping("/{accountBookId}")
    public void addRecord(@PathVariable Long accountBookId, @RequestBody RecordCreateRequest recordWriteDto){
        recordService.addRecord(accountBookId, recordWriteDto);
    }

    @PutMapping("/{recordId}")
    public void updateRecord(@PathVariable Long recordId, @RequestBody RecordUpdateRequest request){
        recordService.updateRecord(recordId, request);
    }

    @DeleteMapping("/{recordId}")
    public void deleteRecord(@PathVariable Long recordId){
        recordService.deleteRecord(recordId);
    }

}
