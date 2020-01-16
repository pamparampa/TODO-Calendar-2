package com.example.radle.todo_calendar2.calendarView.tools.events;

import java.time.LocalDateTime;

public class DayEventsComposer extends AbstractEventsComposer {
    public DayEventsComposer(final LocalDateTime startTime) {
        super(startTime);
    }

    @Override
    LocalDateTime evaluateEndTime(final LocalDateTime startTime) {
        return startTime.plusDays(1);
    }


}
