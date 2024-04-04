package com.account.yomankum.accountBook.dto.response;

import com.account.yomankum.accountBook.domain.tag.Tag;

public record TagResponse (
        Long id,
        String name,
        Long accountBookId
){

    public static TagResponse from(Tag tag){
        return new TagResponse(tag.getId(), tag.getName(), tag.getAccountBook().getId());
    }

}
