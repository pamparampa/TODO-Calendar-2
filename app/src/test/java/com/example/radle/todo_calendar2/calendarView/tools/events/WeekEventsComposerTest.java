package com.example.radle.todo_calendar2.calendarView.tools.events;

import com.example.radle.todo_calendar2.dto.CalendarEvent;
import com.example.radle.todo_calendar2.dto.CalendarEventPart;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class WeekEventsComposerTest {

    private final WeekEventsComposer weekEventsComposer = new WeekEventsComposer(
            LocalDate.of(2019, Month.SEPTEMBER, 11).atStartOfDay());

    @Test
    public void getEventParts_shouldReturnEmptyList_whenThereAreNoEvents() {
        assertEquals(Collections.emptyList(),
                this.weekEventsComposer.getEventsParts(Collections.emptyList()));
    }

    @Test
    public void getEnventParts_shouldReturnTwoWholePartsOfEvents_whenThereAreTwoEventsFitInWeek() {
        final List<CalendarEvent> calendarEvents = Arrays.asList(
                new CalendarEvent("1", "event1",
                        LocalDateTime.of(2019, Month.SEPTEMBER, 16, 10, 0),
                        LocalDateTime.of(2019, Month.SEPTEMBER, 16, 11, 0)),
                new CalendarEvent("2", "event2",
                        LocalDateTime.of(2019, Month.SEPTEMBER, 17, 12, 0),
                        LocalDateTime.of(2019, Month.SEPTEMBER, 17, 13, 0)));

        final List<CalendarEventPart> expectetEventsParts = Arrays.asList(
                new CalendarEventPart("event1",
                        LocalDateTime.of(2019, Month.SEPTEMBER, 16, 10, 0),
                        LocalDateTime.of(2019, Month.SEPTEMBER, 16, 11, 0),
                        calendarEvents.get(0)),
                new CalendarEventPart("event2",
                        LocalDateTime.of(2019, Month.SEPTEMBER, 17, 12, 0),
                        LocalDateTime.of(2019, Month.SEPTEMBER, 17, 13, 0),
                        calendarEvents.get(1)));

        assertEquals(expectetEventsParts, this.weekEventsComposer.getEventsParts(calendarEvents));
    }

    @Test
    public void getEventParts_shouldReturnEmptyList_whenEventsAreBeyondWeek() {
        final List<CalendarEvent> calendarEvents = Arrays.asList(
                new CalendarEvent("1", "event1",
                        LocalDateTime.of(2019, Month.SEPTEMBER, 8, 10, 0),
                        LocalDateTime.of(2019, Month.SEPTEMBER, 8, 11, 0)),
                new CalendarEvent("2", "event2",
                        LocalDateTime.of(2019, Month.SEPTEMBER, 19, 12, 0),
                        LocalDateTime.of(2019, Month.SEPTEMBER, 19, 13, 0)));

        assertEquals(Collections.emptyList(),
                this.weekEventsComposer.getEventsParts(calendarEvents));
    }

    @Test
    public void getEventParts_shouldReturnPartOfEvent_whenEventStartsInPreviousWeekButEndsInThisWeek() {
        final CalendarEvent calendarEvent = new CalendarEvent("1", "event",
                LocalDateTime.of(2019, Month.SEPTEMBER, 9, 15, 0),
                LocalDateTime.of(2019, Month.SEPTEMBER, 11, 10, 0));

        final CalendarEventPart expectedEventPart = new CalendarEventPart("event",
                LocalDateTime.of(2019, Month.SEPTEMBER, 11, 0, 0),
                LocalDateTime.of(2019, Month.SEPTEMBER, 11, 10, 0),
                calendarEvent);

        assertEquals(Collections.singletonList(expectedEventPart),
                this.weekEventsComposer.getEventsParts(Collections.singletonList(calendarEvent)));
    }

    @Test
    public void getEventParts_shouldReturnPartOfEvent_whenEventStartsInThisWeekButEndsInNextWeek() {
        final CalendarEvent calendarEvent = new CalendarEvent("1", "event",
                LocalDateTime.of(2019, Month.SEPTEMBER, 16, 15, 0),
                LocalDateTime.of(2019, Month.SEPTEMBER, 20, 10, 0));

        final CalendarEventPart expectedEventPart = new CalendarEventPart("event",
                LocalDateTime.of(2019, Month.SEPTEMBER, 16, 15, 0),
                LocalDateTime.of(2019, Month.SEPTEMBER, 18, 0, 0),
                calendarEvent);

        assertEquals(Collections.singletonList(expectedEventPart),
                this.weekEventsComposer.getEventsParts(Collections.singletonList(calendarEvent)));
    }

    @Test
    public void getEventParts_shouldReturnOnlyPartsBelongingToWeek_whenDifferentsExist() {
        final List<CalendarEvent> calendarEvents = Arrays.asList(
                new CalendarEvent("1", "event ending on week start date",
                        LocalDateTime.of(2019, Month.SEPTEMBER, 10, 13, 0),
                        LocalDateTime.of(2019, Month.SEPTEMBER, 11, 0, 0)),
                new CalendarEvent("2", "event starting on beginning of next week",
                        LocalDateTime.of(2019, Month.SEPTEMBER, 18, 0, 0),
                        LocalDateTime.of(2019, Month.SEPTEMBER, 18, 20, 5)),
                new CalendarEvent("3", "event filling all the week",
                        LocalDateTime.of(2019, Month.SEPTEMBER, 11, 0, 0),
                        LocalDateTime.of(2019, Month.SEPTEMBER, 18, 0, 0)),
                new CalendarEvent("4", "event starting in previous week and ending in next week",
                        LocalDateTime.of(2019, Month.SEPTEMBER, 10, 1, 0),
                        LocalDateTime.of(2019, Month.SEPTEMBER, 19, 2, 0)));

        final List<CalendarEventPart> expectedEventsParts = Arrays.asList(
                new CalendarEventPart("event filling all the week",
                        LocalDateTime.of(2019, Month.SEPTEMBER, 11, 0, 0),
                        LocalDateTime.of(2019, Month.SEPTEMBER, 18, 0, 0),
                        calendarEvents.get(2)),
                new CalendarEventPart("event starting in previous week and ending in next week",
                        LocalDateTime.of(2019, Month.SEPTEMBER, 11, 0, 0),
                        LocalDateTime.of(2019, Month.SEPTEMBER, 18, 0, 0),
                        calendarEvents.get(3)));

        assertEquals(expectedEventsParts, this.weekEventsComposer.getEventsParts(calendarEvents));
    }
}