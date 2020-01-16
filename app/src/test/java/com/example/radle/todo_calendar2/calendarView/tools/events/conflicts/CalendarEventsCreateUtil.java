package com.example.radle.todo_calendar2.calendarView.tools.events.conflicts;

import com.example.radle.todo_calendar2.calendarView.dto.CalendarEvent;
import com.example.radle.todo_calendar2.calendarView.dto.CalendarEventPart;

import java.time.LocalDateTime;
import java.time.Month;

public class CalendarEventsCreateUtil {
    static CalendarEventPart newEvent(final String event, final int from, final int to) {
        return new CalendarEventPart(event,
                LocalDateTime.of(2020, Month.JANUARY, 6, from, 0, 0),
                LocalDateTime.of(2020, Month.JANUARY, 6, to, 0, 0),
                new CalendarEventStub());
    }

    private static class CalendarEventStub extends CalendarEvent {

        public CalendarEventStub() {
            super("event", LocalDateTime.now(), LocalDateTime.now());
        }
    }
}
