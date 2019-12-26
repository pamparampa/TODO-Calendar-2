package com.example.radle.todo_calendar2.calendarView.dto;

import java.time.LocalDateTime;

public class CalendarEventDayPart extends CalendarEventPart {
    public CalendarEventDayPart(final String title, final LocalDateTime startTime,
                                final LocalDateTime endTime, final CalendarEvent calendarEvent) {
        super(title, startTime, endTime, calendarEvent);
    }
}
