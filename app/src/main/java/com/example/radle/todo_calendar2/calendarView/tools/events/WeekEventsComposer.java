package com.example.radle.todo_calendar2.calendarView.tools.events;

import java.time.LocalDateTime;

public class WeekEventsComposer extends AbstractEventsComposer {

    public WeekEventsComposer(final LocalDateTime startTime) {
        super(startTime);
    }

    @Override
    protected LocalDateTime evaluateEndTime(final LocalDateTime startTime) {
        return startTime.plusWeeks(1);
    }
}
