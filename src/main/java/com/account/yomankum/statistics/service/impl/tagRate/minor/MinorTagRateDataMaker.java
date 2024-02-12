package com.account.yomankum.statistics.service.impl.tagRate.minor;

import com.account.yomankum.accountBook.domain.record.Record;
import com.account.yomankum.statistics.service.impl.tagRate.vo.TagRate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MinorTagRateDataMaker {

    private MinorTagRateDataMaker(){}

    private static final String MINOR_TOTAL = "statistics-totalAmountOfMinorTag";

    public static List<TagRate> createMinorTagRateData(List<Record> records) {
        Map<String, Long> amountsByTags = getAmountsByMinorTags(records);
        long totalAmount = amountsByTags.get(MINOR_TOTAL);

        return amountsByTags.keySet().stream()
                .map(key -> new TagRate(key, totalAmount, amountsByTags.get(key)))
                .sorted()
                .collect(Collectors.toList());
    }

    private static Map<String, Long> getAmountsByMinorTags(List<Record> records) {
        Map<String, Long> amountsByTags = new HashMap<>();
        for(Record record : records){
            for(String minorTag : record.getMinorTag()){
                long amountOfTag = amountsByTags.getOrDefault(minorTag, 0L);
                amountOfTag += record.getMoney();
                amountsByTags.put(minorTag, amountOfTag);

                Long totalAmount = amountsByTags.getOrDefault(MINOR_TOTAL, 0L);
                amountsByTags.put(MINOR_TOTAL, ++totalAmount);
            }
        }
        return amountsByTags;
    }

}
