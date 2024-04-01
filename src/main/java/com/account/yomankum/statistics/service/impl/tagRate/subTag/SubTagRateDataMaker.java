package com.account.yomankum.statistics.service.impl.tagRate.subTag;

import com.account.yomankum.accountBook.domain.record.Record;
import com.account.yomankum.statistics.service.impl.tagRate.vo.TagRate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SubTagRateDataMaker {

    private SubTagRateDataMaker(){}

    public static List<TagRate> createSubTagRateData(List<Record> records) {
        Long totalAmount = getTotalAmount(records);
        Map<String, Long> amountsByTags = getAmountsBySubTags(records);

        return amountsByTags.keySet().stream()
                .map(key -> new TagRate(key, totalAmount, amountsByTags.get(key)))
                .sorted()
                .collect(Collectors.toList());
    }

    private static long getTotalAmount(List<Record> records){
        long amount = 0;
        for(Record record : records){
            amount += record.getSubTags().size() * record.getAmount();
        }
        return amount;
    }

    private static Map<String, Long> getAmountsBySubTags(List<Record> records) {
        Map<String, Long> amountsByTags = new HashMap<>();
        for(Record record : records){
            for(String subTag : record.getSubTags()){
                long amountOfTag = amountsByTags.getOrDefault(subTag, 0L);
                amountOfTag += record.getAmount();
                amountsByTags.put(subTag, amountOfTag);
            }
        }
        return amountsByTags;
    }

}
