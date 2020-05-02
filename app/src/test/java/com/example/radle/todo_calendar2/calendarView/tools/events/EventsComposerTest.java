package com.example.radle.todo_calendar2.calendarView.tools.events;

import com.example.radle.todo_calendar2.calendarView.TimeNotAlignedException;
import com.example.radle.todo_calendar2.calendarView.dto.CalendarEvent;
import com.example.radle.todo_calendar2.calendarView.dto.CalendarEventPartWithWidth;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class EventsComposerTest {

    private EventsComposer subject;

    @Before
    public void init() {
        try {
            this.subject = new EventsComposer(LocalDate.of(2020, Month.MARCH, 30).atStartOfDay());
        } catch (final TimeNotAlignedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void compose_shouldReturnEmptyMap_whenThereAreNoEvents() {
        Assert.assertEquals(Collections.emptySet(), this.subject.compose(Collections.emptyList()));
    }

    @Test
    public void compose_shouldReturnTwoEventsMap_whenThereAreThreeEventsWithoutConflictsButOneOfThemIsFromAnotherWeek() {
        final CalendarEvent event1 = new CalendarEvent("event1",
                LocalDateTime.of(2020, Month.MARCH, 30, 11, 0, 0),
                LocalDateTime.of(2020, Month.MARCH, 30, 13, 30, 0));
        final CalendarEvent event2 = new CalendarEvent("event2",
                LocalDateTime.of(2020, Month.APRIL, 1, 23, 0, 0),
                LocalDateTime.of(2020, Month.APRIL, 2, 0, 20, 0));
        final CalendarEvent event3 = new CalendarEvent("event3",
                LocalDateTime.of(2020, Month.APRIL, 7, 12, 0, 0),
                LocalDateTime.of(2020, Month.APRIL, 7, 12, 30, 0));

        final Set<CalendarEventPartWithWidth> expected = new HashSet<>();

        expected.add(new CalendarEventPartWithWidth(event1, "event1",
                LocalDateTime.of(2020, Month.MARCH, 30, 11, 0, 0),
                LocalDateTime.of(2020, Month.MARCH, 30, 13, 30, 0),
                0, 1, 1));

        expected.add(new CalendarEventPartWithWidth(event2, "event2",
                LocalDateTime.of(2020, Month.APRIL, 1, 23, 0, 0),
                LocalDateTime.of(2020, Month.APRIL, 2, 0, 0, 0),
                0, 1, 1));
        expected.add(new CalendarEventPartWithWidth(event2, "event2",
                LocalDateTime.of(2020, Month.APRIL, 2, 0, 0, 0),
                LocalDateTime.of(2020, Month.APRIL, 2, 0, 20, 0),
                0, 1, 1));

        Assert.assertEquals(expected, this.subject.compose(Arrays.asList(event1, event2, event3)));
    }

    @Test
    public void compose_shouldReturnMapOfEventsWithResolvedConflicts_whenConflictBetweenEventsExist() {
        final CalendarEvent event1 = new CalendarEvent("event1",
                LocalDateTime.of(2020, Month.MARCH, 30, 23, 0, 0),
                LocalDateTime.of(2020, Month.MARCH, 31, 15, 0, 0));
        final CalendarEvent event2 = new CalendarEvent("event2",
                LocalDateTime.of(2020, Month.MARCH, 31, 14, 0, 0),
                LocalDateTime.of(2020, Month.MARCH, 31, 15, 0, 0));
        final CalendarEvent event3 = new CalendarEvent("event3",
                LocalDateTime.of(2020, Month.MARCH, 31, 15, 0, 0),
                LocalDateTime.of(2020, Month.MARCH, 31, 16, 0, 0));
        final CalendarEvent event4 = new CalendarEvent("event4",
                LocalDateTime.of(2020, Month.MARCH, 31, 15, 0, 0),
                LocalDateTime.of(2020, Month.APRIL, 1, 1, 0, 0));
        final CalendarEvent event5 = new CalendarEvent("event5",
                LocalDateTime.of(2020, Month.MARCH, 31, 15, 0, 0),
                LocalDateTime.of(2020, Month.MARCH, 31, 16, 0, 0));

        final Set<CalendarEventPartWithWidth> expected = new HashSet<>();

        expected.add(new CalendarEventPartWithWidth(event1, "event1",
                LocalDateTime.of(2020, Month.MARCH, 30, 23, 0, 0),
                LocalDateTime.of(2020, Month.MARCH, 31, 0, 0, 0),
                0, 1, 1));
        expected.add(new CalendarEventPartWithWidth(event1, "event1",
                LocalDateTime.of(2020, Month.MARCH, 31, 0, 0, 0),
                LocalDateTime.of(2020, Month.MARCH, 31, 15, 0, 0),
                0, 1.5f, 3));
        expected.add(new CalendarEventPartWithWidth(event2, "event2",
                LocalDateTime.of(2020, Month.MARCH, 31, 14, 0, 0),
                LocalDateTime.of(2020, Month.MARCH, 31, 15, 0, 0),
                1.5f, 3, 3));
        expected.add(new CalendarEventPartWithWidth(event3, "event3",
                LocalDateTime.of(2020, Month.MARCH, 31, 15, 0, 0),
                LocalDateTime.of(2020, Month.MARCH, 31, 16, 0, 0),
                0, 1, 3));
        expected.add(new CalendarEventPartWithWidth(event4, "event4",
                LocalDateTime.of(2020, Month.MARCH, 31, 15, 0, 0),
                LocalDateTime.of(2020, Month.APRIL, 1, 0, 0, 0),
                1, 2, 3));
        expected.add(new CalendarEventPartWithWidth(event4, "event4",
                LocalDateTime.of(2020, Month.APRIL, 1, 0, 0, 0),
                LocalDateTime.of(2020, Month.APRIL, 1, 1, 0, 0),
                0, 1, 1));
        expected.add(new CalendarEventPartWithWidth(event5, "event5",
                LocalDateTime.of(2020, Month.MARCH, 31, 15, 0, 0),
                LocalDateTime.of(2020, Month.MARCH, 31, 16, 0, 0),
                2, 3, 3));

        Assert.assertEquals(expected, this.subject.compose(Arrays.asList(
                event1, event2, event3, event4, event5)));
    }

}