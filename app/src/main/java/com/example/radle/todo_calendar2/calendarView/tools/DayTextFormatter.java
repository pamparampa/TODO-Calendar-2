package com.example.radle.todo_calendar2.calendarView.tools;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class DayTextFormatter {

    public String format(final LocalDate date) {
        return date.getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.getDefault())
                + "\n"
                + date.getDayOfMonth();
    }
}
