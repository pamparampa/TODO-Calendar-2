package com.example.radle.todo_calendar2.calendarView.tools;

import java.time.LocalDateTime;

public class HourEventsComposer extends AbstractEventsComposer {
    public HourEventsComposer(final LocalDateTime startTime) {
        super(startTime);
    }

    @Override
    LocalDateTime evaluateEndTime(final LocalDateTime startTime) {
        return startTime.plusHours(1);
    }


}
