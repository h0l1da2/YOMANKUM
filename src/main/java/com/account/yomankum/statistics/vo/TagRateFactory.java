package com.account.yomankum.statistics.vo;

import com.account.yomankum.accountBook.domain.record.Record;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.text.html.HTML.Tag;

public class TagRateFactory {

    private TagRateFactory(){}

    public static List<TagRate> createMajorTagRateData(List<Record> records){
        long totalAmount = records.stream().mapToLong(Record::getMoney).sum();
        Map<String, Long> amountsByTags = getAmountsByTags(records);

        return amountsByTags.keySet().stream()
                .map(key -> new TagRate(key, totalAmount, amountsByTags.get(key)))
                .collect(Collectors.toList());
    }

    private static Map<String, Long> getAmountsByTags(List<Record> records) {
        Map<String, Long> amountsByTags = new HashMap<>();
        for(Record record : records){
            long amountOfTag = amountsByTags.getOrDefault(record.getMajorTag(), 0L);
            amountOfTag += record.getMoney();
            amountsByTags.put(record.getMajorTag(), amountOfTag);
        }
        return amountsByTags;
    }

}
