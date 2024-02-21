package com.account.yomankum.common.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class DatetimeConverter {

    private static final ZoneId zone = ZoneId.of("Asia/Seoul");

    public static LocalDate instantToLocalDate(Instant instant) {
        return LocalDate.ofInstant(instant, zone);
    }
}
