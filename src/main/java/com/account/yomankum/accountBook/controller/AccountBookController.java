package com.account.yomankum.accountBook.controller;

import com.account.yomankum.accountBook.dto.response.AccountBookSimpleDto;
import com.account.yomankum.accountBook.dto.request.AccountBookCreateRequest;
import com.account.yomankum.accountBook.service.AccountBookFinder;
import com.account.yomankum.accountBook.service.AccountBookService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
