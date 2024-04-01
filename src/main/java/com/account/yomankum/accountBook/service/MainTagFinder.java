package com.account.yomankum.accountBook.service;

import com.account.yomankum.accountBook.domain.tag.MainTagRepository;
import com.account.yomankum.accountBook.domain.tag.Tag;
import com.account.yomankum.common.service.SessionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainTagFinder {

    private final MainTagRepository mainTagRepository;

    public List<Tag> findMainTagsByAccountBook(Long accountBookId){
        return mainTagRepository.findByAccountBookId(accountBookId);
    }

}