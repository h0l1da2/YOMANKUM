package com.account.yomankum.accountBook.service;

import com.account.yomankum.accountBook.domain.tag.MainTagRepository;
import com.account.yomankum.accountBook.domain.tag.Tag;
import com.account.yomankum.accountBook.dto.request.MainTagCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainTagService {

    private final MainTagRepository mainTagRepository;

    public Long save(MainTagCreateRequest mainTagCreateRequest) {
        Tag tag = mainTagCreateRequest.toEntity();
        mainTagRepository.save(tag);
        return tag.getId();
    }
}
