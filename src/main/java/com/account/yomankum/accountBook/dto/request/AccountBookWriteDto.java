package com.account.yomankum.accountBook.dto.request;

import com.account.yomankum.accountBook.domain.AccountBook;
import com.account.yomankum.accountBook.domain.AccountBookType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AccountBookWriteDto {

    @NotBlank
    private String name;
    @NotBlank
    private AccountBookType type;

    public AccountBook toEntity(){
        return AccountBook.builder()
                .name(name)
                .type(type)
                .build();
    }

}
