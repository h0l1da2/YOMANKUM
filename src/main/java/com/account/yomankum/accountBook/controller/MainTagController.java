package com.account.yomankum.accountBook.controller;

import com.account.yomankum.accountBook.domain.tag.Tag;
import com.account.yomankum.accountBook.dto.request.MainTagRequest;
import com.account.yomankum.accountBook.service.MainTagFinder;
import com.account.yomankum.accountBook.service.MainTagService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/v1/mainTag")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Tag", description = "태그 api")
public class MainTagController {

    private final MainTagFinder tagFinder;
    private final MainTagService tagService;

    @GetMapping("/{accountBookId}")
    @Operation(summary = "특정 가계부에서 사용되는 모든 대분류 태그")
    public List<Tag> getMainTags(@PathVariable Long accountBookId){
        return tagFinder.findMainTagsByAccountBook(accountBookId);
    }

    @PostMapping("/{accountBookId}")
    @Operation(summary = "대분류 태그 등록")
    public void registerMainTag(@PathVariable Long accountBookId, @RequestBody MainTagRequest mainTagRequest){
        tagService.create(accountBookId, mainTagRequest);
    }

    @PutMapping("/{tagId}")
    @Operation(summary = "태그 변경")
    public void updateTag(@PathVariable Long tagId, @RequestBody MainTagRequest mainTagRequest){
        tagService.update(tagId, mainTagRequest);
    }

    @DeleteMapping("/{tagId}")
    @Operation(summary = "태그 삭제")
    public void deleteTag(@PathVariable Long tagId){
        tagService.delete(tagId);
    }


}
