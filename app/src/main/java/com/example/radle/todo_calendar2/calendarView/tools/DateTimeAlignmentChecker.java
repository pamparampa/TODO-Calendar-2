package com.example.radle.todo_calendar2.calendarView.tools;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class DateTimeAlignmentChecker {

    public boolean isTimeNotAlignedForWeekRow(final LocalDateTime dateTime) {
        return !dateTime.equals(dateTime.toLocalDate().atTime(dateTime.getHour(), 0));
    }

    public boolean isTimeNotAlignedForWeekColumn(final LocalDateTime firstDateTime) {
        return !firstDateTime.isEqual(firstDateTime.toLocalDate().atStartOfDay()) || firstDateTime
                .getDayOfWeek() != DayOfWeek.MONDAY;
    }
}
