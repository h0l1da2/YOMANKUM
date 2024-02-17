package com.account.yomankum.cicd.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@PropertySource("classpath:application.yml")
@RequestMapping("/api/v1/ci-cd")
@Tag(name = "CI/CD 체크", description = "CI/CD 체크 api")
public class CiCdCheckController {

    @Value("${commit.final.number}")
    private String commitNumber;
    @Value("${commit.final.datetime}")
    private String commitDateTime;

    @GetMapping
    public String ciCdCheck() {
        return "마지막 커밋 : " + commitNumber + " , 날짜 : " + commitDateTime;
    }
}
