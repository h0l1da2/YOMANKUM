package com.account.yomankum.controller;

import com.account.yomankum.domain.dto.AccountWriteDto;
import com.account.yomankum.service.AccountBookService;
import com.account.yomankum.web.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final AccountBookService accountBookService;

    @GetMapping
    public ResponseEntity<Response> main() {
        return Response.ok();
    }

    @PostMapping
    public ResponseEntity<Response> write(@Valid List<AccountWriteDto> accountWriteDto) {

        accountBookService.write(accountWriteDto);

        return Response.ok();
    }

}
