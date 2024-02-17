package com.account.yomankum.statistics.service.impl.tagRate.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class TagRate implements Comparable<TagRate> {

    @Schema(description = "태그 명", example = "식사")
    private final String tag;
    @Schema(description = "전체 비율", example = "61")
    private final float rate;
    @Schema(description = "누적 합", example = "410000")
    private final long money;

    public TagRate(String tag, Long totalAmount, Long amountOfTag){
        this.tag = tag;
        this.money = amountOfTag;
        this.rate = ((float) amountOfTag / totalAmount) * 100;
    }

    @Override
    public int compareTo(TagRate o) {
        return Float.compare(o.getRate(), rate);
    }
}
