package com.account.yomankum.accountBook.dto.response;

import com.account.yomankum.accountBook.domain.AccountBook;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountBookSimpleDto {

    private Long id;
    private String name;
    private String type;
    private String createdDateTime;

    public static AccountBookSimpleDto from(AccountBook entity){
        return AccountBookSimpleDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .type(entity.getType().getName())
                .build();
    }

}
