package com.example.radle.todo_calendar2.calendarView.tools;

import com.example.radle.todo_calendar2.calendarView.dto.CalendarEvent;
import com.example.radle.todo_calendar2.calendarView.dto.CalendarEventHourPart;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class HourEventsComposerTest {
    private final HourEventsComposer hourEventsComposer = new HourEventsComposer(
            LocalDateTime.of(2019, Month.SEPTEMBER, 11, 12, 0));

    @Test
    public void getEventParts_shouldReturnEmptyList_whenThereAreNoEvents() {
        assertEquals(Collections.emptyList(),
                this.hourEventsComposer.getEventsParts(Collections.emptyList()));
    }

    @Test
    public void getEnventParts_shouldReturnTwoWholePartsOfEvents_whenThereAreTwoEventsFitInHour() {
        final List<CalendarEvent> calendarEvents = Arrays.asList(
                new CalendarEvent("event1",
                        LocalDateTime.of(2019, Month.SEPTEMBER, 11, 12, 10),
                        LocalDateTime.of(2019, Month.SEPTEMBER, 11, 12, 30)),
                new CalendarEvent("event2",
                        LocalDateTime.of(2019, Month.SEPTEMBER, 11, 12, 30),
                        LocalDateTime.of(2019, Month.SEPTEMBER, 11, 12, 50)));

        final List<CalendarEventHourPart> expectetEventsParts = Arrays.asList(
                new CalendarEventHourPart("event1",
                        LocalDateTime.of(2019, Month.SEPTEMBER, 11, 12, 10),
                        LocalDateTime.of(2019, Month.SEPTEMBER, 11, 12, 30),
                        calendarEvents.get(0)),
                new CalendarEventHourPart("event2",
                        LocalDateTime.of(2019, Month.SEPTEMBER, 11, 12, 30),
                        LocalDateTime.of(2019, Month.SEPTEMBER, 11, 12, 50),
                        calendarEvents.get(1)));

        assertEquals(expectetEventsParts, this.hourEventsComposer.getEventsParts(calendarEvents));
    }

    @Test
    public void getEventParts_shouldReturnEmptyList_whenEventsAreBeyondHour() {
        final List<CalendarEvent> calendarEvents = Arrays.asList(
                new CalendarEvent("event1",
                        LocalDateTime.of(2019, Month.SEPTEMBER, 11, 10, 0),
                        LocalDateTime.of(2019, Month.SEPTEMBER, 11, 11, 0)),
                new CalendarEvent("event2",
                        LocalDateTime.of(2019, Month.SEPTEMBER, 11, 14, 0),
                        LocalDateTime.of(2019, Month.SEPTEMBER, 11, 15, 0)));

        assertEquals(Collections.emptyList(),
                this.hourEventsComposer.getEventsParts(calendarEvents));
    }

    @Test
    public void getEventParts_shouldReturnPartOfEvent_whenEventStartsInPreviousHourButEndsInThisHour() {
        final CalendarEvent calendarEvent = new CalendarEvent("event",
                LocalDateTime.of(2019, Month.SEPTEMBER, 11, 11, 30),
                LocalDateTime.of(2019, Month.SEPTEMBER, 11, 12, 30));

        final CalendarEventHourPart expectedEventPart = new CalendarEventHourPart("event",
                LocalDateTime.of(2019, Month.SEPTEMBER, 11, 12, 0),
                LocalDateTime.of(2019, Month.SEPTEMBER, 11, 12, 30),
                calendarEvent);

        assertEquals(Collections.singletonList(expectedEventPart),
                this.hourEventsComposer.getEventsParts(Collections.singletonList(calendarEvent)));
    }

    @Test
    public void getEventParts_shouldReturnPartOfEvent_whenEventStartsInThisHourButEndsInNextHour() {
        final CalendarEvent calendarEvent = new CalendarEvent("event",
                LocalDateTime.of(2019, Month.SEPTEMBER, 11, 12, 30),
                LocalDateTime.of(2019, Month.SEPTEMBER, 11, 13, 30));

        final CalendarEventHourPart expectedEventPart = new CalendarEventHourPart("event",
                LocalDateTime.of(2019, Month.SEPTEMBER, 11, 12, 30),
                LocalDateTime.of(2019, Month.SEPTEMBER, 11, 13, 0),
                calendarEvent);

        assertEquals(Collections.singletonList(expectedEventPart),
                this.hourEventsComposer.getEventsParts(Collections.singletonList(calendarEvent)));
    }

    @Test
    public void getEventParts_shouldReturnPartOfEventWithNoTitleShift_whenThePartIsTheBeginningOfNewDay() {

    }

    @Test
    public void getEventParts_shouldReturnOnlyPartsBelongingToWeek_whenDifferentsExist() {
        final List<CalendarEvent> calendarEvents = Arrays.asList(
                new CalendarEvent("event ending on current hour beginning",
                        LocalDateTime.of(2019, Month.SEPTEMBER, 11, 10, 0),
                        LocalDateTime.of(2019, Month.SEPTEMBER, 11, 12, 0)),
                new CalendarEvent("event starting on beginning of next hour",
                        LocalDateTime.of(2019, Month.SEPTEMBER, 11, 13, 0),
                        LocalDateTime.of(2019, Month.SEPTEMBER, 11, 13, 5)),
                new CalendarEvent("event filling all the hour",
                        LocalDateTime.of(2019, Month.SEPTEMBER, 11, 12, 0),
                        LocalDateTime.of(2019, Month.SEPTEMBER, 11, 13, 0)),
                new CalendarEvent("event starting in previous hour and ending in next hour",
                        LocalDateTime.of(2019, Month.SEPTEMBER, 11, 10, 0),
                        LocalDateTime.of(2019, Month.SEPTEMBER, 11, 14, 0)),
                new CalendarEvent("event fitting the hour but in other day",
                        LocalDateTime.of(2019, Month.SEPTEMBER, 10, 12, 10),
                        LocalDateTime.of(2019, Month.SEPTEMBER, 10, 12, 30)));

        final List<CalendarEventHourPart> expectedEventsParts = Arrays.asList(
                new CalendarEventHourPart("event filling all the hour",
                        LocalDateTime.of(2019, Month.SEPTEMBER, 11, 12, 0),
                        LocalDateTime.of(2019, Month.SEPTEMBER, 11, 13, 0),
                        calendarEvents.get(2)),
                new CalendarEventHourPart("event starting in previous hour and ending in next hour",
                        LocalDateTime.of(2019, Month.SEPTEMBER, 11, 12, 0),
                        LocalDateTime.of(2019, Month.SEPTEMBER, 11, 13, 0),
                        calendarEvents.get(3)));

        assertEquals(expectedEventsParts, this.hourEventsComposer.getEventsParts(calendarEvents));
    }
}