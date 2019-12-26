package com.example.radle.todo_calendar2.calendarView.tools;

import com.example.radle.todo_calendar2.calendarView.dto.CalendarEvent;
import com.example.radle.todo_calendar2.calendarView.dto.CalendarEventDayPart;
import com.example.radle.todo_calendar2.calendarView.dto.CalendarEventPart;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EventsConflictsResolverTest {

    private final EventsConflictsResolver conflictsResolver = new EventsConflictsResolver();

    @Test
    public void resolve_shouldReturnEmptyList_whenNoDtosPassed() {
        assertTrue(this.conflictsResolver.resolve(Collections.emptyList()).isEmpty());
    }

    @Test
    public void resoulve_shouldReturnOneAlignedDto_whenOneDtoPassed() {
        final LocalDateTime startTime = LocalDateTime.of(2019, Month.NOVEMBER, 23, 14, 0);
        final LocalDateTime endTime = LocalDateTime.of(2019, Month.NOVEMBER, 23, 15, 0);
        final CalendarEvent calendarEvent = new CalendarEvent("title", startTime, endTime);
        final CalendarEventDayPart expectedDayPart =
                new CalendarEventDayPart("title", startTime, endTime, calendarEvent);
        assertEquals(Collections.singletonList(expectedDayPart),
                this.conflictsResolver.resolve(Collections.singletonList(
                        new CalendarEventPart("title", startTime, endTime, calendarEvent))));
    }

}