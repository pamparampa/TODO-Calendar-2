package com.example.radle.todo_calendar2.calendarView.tools;

import java.time.LocalDateTime;

public class WeekBeginningDateTimeProvider {
    public LocalDateTime getWeekBeginning(final LocalDateTime dateTime) {
        return dateTime.minusDays(numberOfPassedDaysFromMonday(dateTime)).toLocalDate().atStartOfDay();
    }

    public LocalDateTime getNextWeekBeginning(final LocalDateTime dateTime) {
        return getWeekBeginning(dateTime.plusWeeks(1));
    }

    public LocalDateTime getPrevWeekBeginning(final LocalDateTime dateTime) {
        return getWeekBeginning(dateTime.minusWeeks(1));
    }

    private int numberOfPassedDaysFromMonday(final LocalDateTime dateTime) {
        return dateTime.getDayOfWeek().getValue() - 1;
    }
}
