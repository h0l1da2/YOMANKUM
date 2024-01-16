package com.account.yomankum.accountBook.controller;

import com.account.yomankum.accountBook.dto.request.RecordWriteDto;
import com.account.yomankum.accountBook.dto.response.AccountBookSimpleDto;
import com.account.yomankum.accountBook.dto.request.AccountBookWriteDto;
import com.account.yomankum.accountBook.service.AccountBookService;
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
@RequestMapping("/api/v1/account")
public class AccountBookController {

    private final AccountBookService accountBookService;

    @PostMapping
    public Long create(@RequestBody AccountBookWriteDto accountWriteDto){
        return accountBookService.create(accountWriteDto);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody String name){
        accountBookService.update(id, name);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id")Long id){
        accountBookService.delete(id);
    }

    @GetMapping
    public List<AccountBookSimpleDto> get(){
        return accountBookService.findByUser();
    }

}
