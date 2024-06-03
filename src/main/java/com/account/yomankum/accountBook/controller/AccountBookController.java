package com.account.yomankum.accountBook.controller;

import com.account.yomankum.accountBook.dto.request.AccountBookCreateRequest;
import com.account.yomankum.accountBook.dto.request.AccountBookInviteRequest;
import com.account.yomankum.accountBook.dto.response.AccountBookSimpleDto;
import com.account.yomankum.accountBook.service.AccountBookFinder;
import com.account.yomankum.accountBook.service.AccountBookService;
import com.account.yomankum.auth.common.Auth;
import com.account.yomankum.auth.common.LoginUser;
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
    public Long create(@Auth LoginUser loginUser, @RequestBody AccountBookCreateRequest accountWriteDto){
        return accountBookService.create(accountWriteDto, loginUser.getUserId());
    }

    @PutMapping("/{id}")
    public void update(@Auth LoginUser loginUser, @PathVariable Long id, @RequestBody String name){
        accountBookService.update(id, name, loginUser.getUserId());
    }

    @DeleteMapping("/{id}")
    public void delete(@Auth LoginUser loginUser, @PathVariable("id")Long id){
        accountBookService.delete(id, loginUser.getUserId());
    }

    @GetMapping
    public List<AccountBookSimpleDto> get(@Auth LoginUser loginUser){
        return accountBookFinder.findByUser(loginUser.getUserId());
    }

    @PostMapping("/{id}/invite")
    public void invite(@Auth LoginUser loginUser, @PathVariable("id") Long id, @RequestBody AccountBookInviteRequest accountBookInviteRequest) {
        accountBookService.invite(id, accountBookInviteRequest, loginUser.getUserId());
    }

}
