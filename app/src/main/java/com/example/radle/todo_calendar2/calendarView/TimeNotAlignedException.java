package com.example.radle.todo_calendar2.calendarView;

import java.time.temporal.TemporalUnit;

public class TimeNotAlignedException extends Exception {
    private static final String MESSAGE = "Time should be aligned to %s but has not zero values " +
            "in less significant positions";

    public TimeNotAlignedException(final TemporalUnit unit) {
        super(String.format(MESSAGE, unit));
    }
}
