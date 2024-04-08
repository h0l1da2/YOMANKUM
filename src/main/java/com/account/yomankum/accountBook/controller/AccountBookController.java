package com.account.yomankum.accountBook.controller;

import com.account.yomankum.accountBook.dto.request.AccountBookCreateRequest;
import com.account.yomankum.accountBook.dto.response.AccountBookSimpleDto;
import com.account.yomankum.accountBook.service.AccountBookFinder;
import com.account.yomankum.accountBook.service.AccountBookService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
@Tag(name = "AccountBook", description = "가계부 api")
public class AccountBookController {

    private final AccountBookService accountBookService;
    private final AccountBookFinder accountBookFinder;

    @PostMapping
    public Long create(@RequestBody AccountBookCreateRequest accountWriteDto){
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
        return accountBookFinder.findByUser();
    }

}
