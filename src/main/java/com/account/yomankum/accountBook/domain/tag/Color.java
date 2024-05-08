package com.account.yomankum.accountBook.domain.tag;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Color {

    private String color;
    private String bgc;

}
