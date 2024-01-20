package com.account.yomankum.accountBook.dto.response;

import com.account.yomankum.accountBook.domain.AccountBook;
import lombok.Builder;
import lombok.Getter;


@Builder
public record AccountBookSimpleDto (
        Long id,
        String name,
        String type,
        String createdDateTime
) {
    public static AccountBookSimpleDto from(AccountBook entity){
        return AccountBookSimpleDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .type(entity.getType().getTitle())
                .build();
    }
}
