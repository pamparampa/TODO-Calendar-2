package com.example.radle.todo_calendar2.calendarView.tools.events.conflicts;

import com.example.radle.todo_calendar2.calendarView.dto.CalendarEventPart;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static com.example.radle.todo_calendar2.calendarView.tools.events.conflicts.CalendarEventsCreateUtil.newEvent;
import static org.junit.Assert.assertEquals;

public class DayEventsArrangementTest {

    DayEventsArrangement subject = new DayEventsArrangement();

    @Test
    public void arrange_shouldReturnEmptyList_whenNoEventsPassed() {
        assertEquals(Collections.emptyList(), this.subject.arrange(Collections.emptyList()));
    }

    @Test
    public void arrange_shouldReturnOneSplitColumnWithAllEvents_whenEventsHasNoConflicts() {
        final CalendarEventPart event1 = newEvent("event1", 10, 12);
        final CalendarEventPart event2 = newEvent("event2", 14, 15);

        assertEquals(Collections.singletonList(Arrays.asList(event1, event2)),
                this.subject.arrange(Arrays.asList(event1, event2)));
    }

    @Test
    public void arrange_shouldReturnTwoSplitColumns_whenTwoElementsHasConflict() {
        final CalendarEventPart event1 = newEvent("event1", 10, 12);
        final CalendarEventPart event2 = newEvent("event2", 11, 13);

        assertEquals(
                Arrays.asList(Collections.singletonList(event1), Collections.singletonList(event2)),
                this.subject.arrange(Arrays.asList(event1, event2)));
    }

    @Test
    public void arrange_shouldReturnThreeSplitColumns_whenThreeElementsHasConflicts() {
        final CalendarEventPart event1 = newEvent("event1", 10, 12);
        final CalendarEventPart event2 = newEvent("event2", 11, 13);
        final CalendarEventPart event3 = newEvent("event3", 20, 21);

        assertEquals(
                Arrays.asList(Arrays.asList(event1, event3), Collections.singletonList(event2)),
                this.subject.arrange(Arrays.asList(event1, event2, event3)));
    }

    @Test
    public void arrange_shouldReturnTwoSplitColumns_whenThereAreFourElementsButThereAreOnlyOneConflict() {
        final CalendarEventPart event1 = newEvent("event1", 10, 12);
        final CalendarEventPart event2 = newEvent("event2", 19, 21);
        final CalendarEventPart event3 = newEvent("event3", 20, 21);
        final CalendarEventPart event4 = newEvent("event4", 22, 23);

        assertEquals(
                Arrays.asList(Arrays.asList(event1, event2, event4),
                        Collections.singletonList(event3)),
                this.subject.arrange(Arrays.asList(event1, event2, event3, event4)));
    }

    @Test
    public void arrange_shouldReturnCorrectSplitColumns_whenThereAreManyEventsWithManyConflicts() {
        final CalendarEventPart event1 = newEvent("event1", 7, 10);
        final CalendarEventPart event2 = newEvent("event2", 7, 9);
        final CalendarEventPart event3 = newEvent("event3", 11, 13);
        final CalendarEventPart event4 = newEvent("event4", 8, 12);
        final CalendarEventPart event5 = newEvent("event5", 7, 8);
        final CalendarEventPart event6 = newEvent("event6", 10, 18);
        final CalendarEventPart event7 = newEvent("eveny7", 10, 13);

        assertEquals(Arrays.asList(
                Arrays.asList(event1, event3),
                Arrays.asList(event2, event6),
                Arrays.asList(event4, event5),
                Collections.singletonList(event7)),
                this.subject.arrange(Arrays.asList(
                        event1, event2, event3, event4, event5, event6, event7)));
    }
}