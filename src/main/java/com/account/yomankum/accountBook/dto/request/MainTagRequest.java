package com.account.yomankum.accountBook.dto.request;

import com.account.yomankum.accountBook.domain.tag.Tag;

public record MainTagRequest(
        String tagName
) {

    public Tag toEntity() {
        return Tag.of(tagName);
    }
}
