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

    // TODO 프론트에서 서식에 맞춰서 NotNull 데이터만 읽어올 수 있도록 전달하기. 완성되지 않은 데이터는 저장할 수 없다.
    @PostMapping
    public ResponseEntity<Response> write(@Valid List<AccountWriteDto> accountWriteDtoList) {

        accountBookService.write(accountWriteDtoList);

        return Response.ok();
    }

}
