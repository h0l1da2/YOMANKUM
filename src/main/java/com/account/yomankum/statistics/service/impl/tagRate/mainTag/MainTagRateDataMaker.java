package com.account.yomankum.statistics.service.impl.tagRate.mainTag;

import com.account.yomankum.accountBook.domain.record.Record;
import com.account.yomankum.accountBook.domain.tag.Tag;
import com.account.yomankum.statistics.service.impl.tagRate.vo.TagRate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainTagRateDataMaker {

    private MainTagRateDataMaker(){}

    public static List<TagRate> createMainTagRateData(List<Record> records){
        long totalAmount = records.stream().mapToLong(Record::getAmount).sum();
        Map<Tag, Long> amountsByTags = getAmountsByMainTags(records);

        return amountsByTags.keySet().stream()
                .map(key -> new TagRate(key.getName(), totalAmount, amountsByTags.get(key)))
                .sorted()
                .collect(Collectors.toList());
    }

    private static Map<Tag, Long> getAmountsByMainTags(List<Record> records) {
        Map<Tag, Long> amountsByTags = new HashMap<>();
        for(Record record : records){
            long amountOfTag = amountsByTags.getOrDefault(record.getMainTag(), 0L);
            amountOfTag += record.getAmount();
            amountsByTags.put(record.getMainTag(), amountOfTag);
        }
        return amountsByTags;
    }

}
