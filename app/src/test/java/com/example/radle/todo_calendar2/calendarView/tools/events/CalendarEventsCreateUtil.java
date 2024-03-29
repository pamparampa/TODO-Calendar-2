package com.example.radle.todo_calendar2.calendarView.tools.events;

import com.example.radle.todo_calendar2.dto.CalendarEvent;
import com.example.radle.todo_calendar2.dto.CalendarEventPart;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

public class CalendarEventsCreateUtil {
    public static CalendarEventPart newEvent(final String event, final int from, final int to) {
        return new CalendarEventPart(event,
                LocalDateTime.of(2020, Month.JANUARY, 6, from, 0, 0),
                LocalDateTime.of(2020, Month.JANUARY, 6, to, 0, 0),
                new CalendarEventStub());
    }

    public static CalendarEventPart newEvent(final String event, final LocalTime from,
                                             final LocalTime to) {
        return new CalendarEventPart(event,
                LocalDate.of(2020, Month.JANUARY, 6).atTime(from),
                LocalDate.of(2020, Month.JANUARY, 6).atTime(to),
                new CalendarEventStub());
    }

    public static CalendarEventPart allDayEvent(final String event) {
        return new CalendarEventPart(event,
                LocalDateTime.of(2020, Month.JANUARY, 6, 0, 0, 0),
                LocalDateTime.of(2020, Month.JANUARY, 7, 0, 0, 0),
                new CalendarEventStub());
    }

    private static class CalendarEventStub extends CalendarEvent {

        public CalendarEventStub() {
            super("1", "event", LocalDateTime.now(), LocalDateTime.now());
        }
    }
}
