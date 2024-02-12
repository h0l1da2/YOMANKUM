package com.account.yomankum.statistics.service.impl.tagRate.vo;

import lombok.Getter;

@Getter
public class TagRate implements Comparable<TagRate> {

    private final String tag;
    private final float rate;
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
