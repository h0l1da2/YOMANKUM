package com.account.yomankum.accountBook.service;

import com.account.yomankum.accountBook.domain.AccountBook;
import com.account.yomankum.accountBook.domain.tag.MainTagRepository;
import com.account.yomankum.accountBook.domain.tag.Tag;
import com.account.yomankum.accountBook.dto.request.MainTagRequest;
import com.account.yomankum.common.exception.BadRequestException;
import com.account.yomankum.common.exception.Exception;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainTagService {

    private final AccountBookFinder accountBookFinder;
    private final MainTagRepository mainTagRepository;

    public Tag create(Long accountBookId, MainTagRequest mainTagCreateRequest, Long requesterId) {
        Tag tag = mainTagCreateRequest.toEntity();
        mainTagRepository.save(tag);
        AccountBook accountBook = accountBookFinder.findById(accountBookId);
        accountBook.addTag(tag, requesterId);
        return tag;
    }

    public void delete(Long tagId, Long requesterId) {
        Tag tag = findTag(tagId);
        tag.delete(requesterId);
        mainTagRepository.delete(tag);
    }

    public Tag update(Long tagId, MainTagRequest mainTagRequest, Long requesterId) {
        Tag tag = findTag(tagId);
        tag.update(mainTagRequest.tagName(), requesterId);
        return tag;
    }

    private Tag findTag(Long tagId) {
        return mainTagRepository.findById(tagId).orElseThrow(() -> new BadRequestException(Exception.TAG_NOT_FOUND));
    }

}