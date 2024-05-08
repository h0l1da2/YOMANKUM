package com.account.yomankum.statistics.service.impl.tagRate.vo;

import com.account.yomankum.accountBook.domain.tag.Tag;
import com.account.yomankum.statistics.dto.StatisticsResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class TagRate implements StatisticsResponse, Comparable<TagRate> {

    @Schema(description = "태그", example = "식사")
    private final String tag;
    @Schema(description = "전체 비율", example = "61")
    private final float rate;
    @Schema(description = "누적 합", example = "410000")
    private final long money;

    public TagRate(String tagName, Long totalAmount, Long amountOfTag){
        this.tag = tagName;
        this.money = amountOfTag;
        this.rate = ((float) amountOfTag / totalAmount) * 100;
    }

    @Override
    public int compareTo(TagRate o) {
        return Float.compare(o.getRate(), rate);
    }
}
