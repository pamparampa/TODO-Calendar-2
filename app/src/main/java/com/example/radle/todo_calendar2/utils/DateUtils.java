package com.example.radle.todo_calendar2.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateUtils {

    private final static int NUMBER_OF_MILLIS_IN_SECOND = 1000;

    public static long toUtcEpochMillis(LocalDateTime dateTime) {
        return dateTime.toEpochSecond(ZoneId.systemDefault().getRules().getOffset(dateTime)) * NUMBER_OF_MILLIS_IN_SECOND;
    }
}
