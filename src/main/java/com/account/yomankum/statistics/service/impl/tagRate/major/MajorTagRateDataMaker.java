package com.account.yomankum.statistics.service.impl.tagRate.major;

import com.account.yomankum.accountBook.domain.record.Record;
import com.account.yomankum.statistics.service.impl.tagRate.vo.TagRate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MajorTagRateDataMaker {

    private MajorTagRateDataMaker(){}

    public static List<TagRate> createMajorTagRateData(List<Record> records){
        long totalAmount = records.stream().mapToLong(Record::getMoney).sum();
        Map<String, Long> amountsByTags = getAmountsByMajorTags(records);

        return amountsByTags.keySet().stream()
                .map(key -> new TagRate(key, totalAmount, amountsByTags.get(key)))
                .sorted()
                .collect(Collectors.toList());
    }

    private static Map<String, Long> getAmountsByMajorTags(List<Record> records) {
        Map<String, Long> amountsByTags = new HashMap<>();
        for(Record record : records){
            long amountOfTag = amountsByTags.getOrDefault(record.getMajorTag(), 0L);
            amountOfTag += record.getMoney();
            amountsByTags.put(record.getMajorTag(), amountOfTag);
        }
        return amountsByTags;
    }

}
