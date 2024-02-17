package com.account.yomankum.accountBook.controller;

import com.account.yomankum.accountBook.service.TagFinder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tag")
@Tag(name = "Tag", description = "태그 api")
public class TagController {

    private final TagFinder tagFinder;

    @GetMapping("/{accountBookId}")
    @Operation(summary = "특정 가계부에서 사용되는 모든 대분류 태그", description = "기간조건을 이용하여 특정 기간에 사용된 대분류 태그만 검색할 수 있다.")
    public List<String> getMajorTags(@PathVariable Long accountBookId,
            @RequestParam LocalDate from,
            @RequestParam LocalDate to){
        return tagFinder.findMajorTagsByAccountBook(accountBookId, from, to);
    }

}
