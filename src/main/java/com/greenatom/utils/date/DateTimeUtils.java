package com.greenatom.utils.date;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public final class DateTimeUtils {

    private DateTimeUtils() {
    }

    public static Date getTodayDate() {
        return Date.from(
                LocalDate.now()
                        .atStartOfDay(
                                ZoneId.of("Europe/Moscow")
                        )
                        .toInstant()
        );
    }
}
