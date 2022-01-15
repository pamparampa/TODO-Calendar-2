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

public class DayEventsComposerTest {
    private final DayEventsComposer dayEventsComposer = new DayEventsComposer(
            LocalDate.of(2019, Month.SEPTEMBER, 11).atStartOfDay());

    @Test
    public void getEventParts_shouldReturnEmptyList_whenThereAreNoEvents() {
        assertEquals(Collections.emptyList(),
                this.dayEventsComposer.getEventsParts(Collections.emptyList()));
    }

    @Test
    public void getEnventParts_shouldReturnTwoWholePartsOfEvents_whenThereAreTwoEventsFitInDay() {
        final List<CalendarEvent> calendarEvents = Arrays.asList(
                new CalendarEvent("1", "event1",
                        LocalDateTime.of(2019, Month.SEPTEMBER, 11, 12, 0),
                        LocalDateTime.of(2019, Month.SEPTEMBER, 11, 12, 30)),
                new CalendarEvent("2", "event2",
                        LocalDateTime.of(2019, Month.SEPTEMBER, 11, 20, 0),
                        LocalDateTime.of(2019, Month.SEPTEMBER, 11, 23, 0)));

        final List<CalendarEventPart> expectetEventsParts = Arrays.asList(
                new CalendarEventPart("event1",
                        LocalDateTime.of(2019, Month.SEPTEMBER, 11, 12, 0),
                        LocalDateTime.of(2019, Month.SEPTEMBER, 11, 12, 30),
                        calendarEvents.get(0)),
                new CalendarEventPart("event2",
                        LocalDateTime.of(2019, Month.SEPTEMBER, 11, 20, 0),
                        LocalDateTime.of(2019, Month.SEPTEMBER, 11, 23, 0),
                        calendarEvents.get(1)));

        assertEquals(expectetEventsParts, this.dayEventsComposer.getEventsParts(calendarEvents));
    }

    @Test
    public void getEventParts_shouldReturnEmptyList_whenEventsAreBeyondDay() {
        final List<CalendarEvent> calendarEvents = Arrays.asList(
                new CalendarEvent("1", "event1",
                        LocalDateTime.of(2019, Month.SEPTEMBER, 10, 10, 0),
                        LocalDateTime.of(2019, Month.SEPTEMBER, 10, 11, 0)),
                new CalendarEvent("2", "event2",
                        LocalDateTime.of(2019, Month.SEPTEMBER, 12, 14, 0),
                        LocalDateTime.of(2019, Month.SEPTEMBER, 12, 15, 0)));

        assertEquals(Collections.emptyList(),
                this.dayEventsComposer.getEventsParts(calendarEvents));
    }

    @Test
    public void getEventParts_shouldReturnPartOfEvent_whenEventStartsInPreviousDayButEndsInThisDay() {
        final CalendarEvent calendarEvent = new CalendarEvent("1", "event",
                LocalDateTime.of(2019, Month.SEPTEMBER, 10, 23, 0),
                LocalDateTime.of(2019, Month.SEPTEMBER, 11, 1, 0));

        final CalendarEventPart expectedEventPart = new CalendarEventPart("event",
                LocalDateTime.of(2019, Month.SEPTEMBER, 11, 0, 0),
                LocalDateTime.of(2019, Month.SEPTEMBER, 11, 1, 0),
                calendarEvent);

        assertEquals(Collections.singletonList(expectedEventPart),
                this.dayEventsComposer.getEventsParts(Collections.singletonList(calendarEvent)));
    }

    @Test
    public void getEventParts_shouldReturnPartOfEvent_whenEventStartsInThisDayButEndsInNextDay() {
        final CalendarEvent calendarEvent = new CalendarEvent("1", "event",
                LocalDateTime.of(2019, Month.SEPTEMBER, 11, 23, 0),
                LocalDateTime.of(2019, Month.SEPTEMBER, 12, 1, 30));

        final CalendarEventPart expectedEventPart = new CalendarEventPart("event",
                LocalDateTime.of(2019, Month.SEPTEMBER, 11, 23, 0),
                LocalDateTime.of(2019, Month.SEPTEMBER, 12, 0, 0),
                calendarEvent);

        assertEquals(Collections.singletonList(expectedEventPart),
                this.dayEventsComposer.getEventsParts(Collections.singletonList(calendarEvent)));
    }

    @Test
    public void getEventParts_shouldReturnOnlyPartsBelongingToDay_whenDifferentEventsExist() {
        final List<CalendarEvent> calendarEvents = Arrays.asList(
                new CalendarEvent("1", "event ending on current day beginning",
                        LocalDateTime.of(2019, Month.SEPTEMBER, 10, 22, 0),
                        LocalDateTime.of(2019, Month.SEPTEMBER, 11, 0, 0)),
                new CalendarEvent("2", "event starting on beginning of next day",
                        LocalDateTime.of(2019, Month.SEPTEMBER, 12, 0, 0),
                        LocalDateTime.of(2019, Month.SEPTEMBER, 12, 1, 50)),
                new CalendarEvent("3", "event filling whole day",
                        LocalDateTime.of(2019, Month.SEPTEMBER, 11, 0, 0),
                        LocalDateTime.of(2019, Month.SEPTEMBER, 12, 0, 0)),
                new CalendarEvent("4", "event starting in previous day and ending in next day",
                        LocalDateTime.of(2019, Month.SEPTEMBER, 10, 23, 0),
                        LocalDateTime.of(2019, Month.SEPTEMBER, 12, 2, 0)));

        final List<CalendarEventPart> expectedEventsParts = Arrays.asList(
                new CalendarEventPart("event filling whole day",
                        LocalDateTime.of(2019, Month.SEPTEMBER, 11, 0, 0),
                        LocalDateTime.of(2019, Month.SEPTEMBER, 12, 0, 0),
                        calendarEvents.get(2)),
                new CalendarEventPart("event starting in previous day and ending in next day",
                        LocalDateTime.of(2019, Month.SEPTEMBER, 11, 0, 0),
                        LocalDateTime.of(2019, Month.SEPTEMBER, 12, 0, 0),
                        calendarEvents.get(3)));

        assertEquals(expectedEventsParts, this.dayEventsComposer.getEventsParts(calendarEvents));
    }
}