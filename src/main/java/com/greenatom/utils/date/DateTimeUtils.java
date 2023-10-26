package com.greenatom.utils.date;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateTimeUtils {

    public static Date getTodayDate() {
        return Date.from(
                LocalDate.now()
                        .atStartOfDay(
                                ZoneId.of( "Europe/Moscow" )
                        )
                        .toInstant()
        );
    }
}
