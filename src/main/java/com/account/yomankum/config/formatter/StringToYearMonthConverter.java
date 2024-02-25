package com.account.yomankum.config.formatter;

import java.time.YearMonth;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToYearMonthConverter implements Converter<String, YearMonth> {

    @Override
    public YearMonth convert(String source) {
        return YearMonth.parse(source);
    }
}