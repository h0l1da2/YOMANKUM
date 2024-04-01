package com.account.yomankum.accountBook.service;

import com.account.yomankum.accountBook.domain.tag.MainTagRepository;
import com.account.yomankum.accountBook.domain.tag.Tag;
import com.account.yomankum.accountBook.dto.request.MainTagRequest;
import com.account.yomankum.common.exception.BadRequestException;
import com.account.yomankum.common.exception.Exception;
import com.account.yomankum.common.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainTagService {

    private final MainTagRepository mainTagRepository;
    private final SessionService sessionService;

    public Long save(MainTagRequest mainTagCreateRequest) {
        Tag tag = mainTagCreateRequest.toEntity();
        checkAuthorizedRequest(tag);
        mainTagRepository.save(tag);
        return tag.getId();
    }

    public void delete(Long tagId) {
        Tag tag = findTag(tagId);
        checkAuthorizedRequest(tag);
        mainTagRepository.delete(tag);
    }

    public void update(Long tagId, MainTagRequest mainTagRequest) {
        Tag tag = findTag(tagId);
        checkAuthorizedRequest(tag);
        tag.update(mainTagRequest.tagName());
    }

    private Tag findTag(Long tagId){
        return mainTagRepository.findById(tagId).orElseThrow(()->new BadRequestException(Exception.TAG_NOT_FOUND));
    }

    private void checkAuthorizedRequest(Tag tag){
        Long userId = sessionService.getSessionUserId();
        tag.checkAuthorizedUser(userId);
    }
}
