package com.example.radle.todo_calendar2.calendarView.tools.events.conflicts;

import com.example.radle.todo_calendar2.calendarView.dto.CalendarEventPart;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static com.example.radle.todo_calendar2.calendarView.tools.events.conflicts.CalendarEventsCreateUtil.newEvent;

public class DayEventsEnlargementTest {

    private final DayEventsEnlargement subject = new DayEventsEnlargement();

    @Test
    public void enlarge_shouldReturnEmptyList_whenNoEventsPassed() {
        Assert.assertEquals(Collections.emptyList(), this.subject.enlarge(Collections.emptyList()));
    }


    @Test
    /*
    [][][] => []->[]->[]
     */
    public void oneChainNoResizing() {

        final CalendarEventPart event1 = newEvent("event1", 8, 9);
        final CalendarEventPart event2 = newEvent("event2", 8, 9);
        final CalendarEventPart event3 = newEvent("event3", 8, 9);

        Assert.assertEquals(Collections.singletonList(
                new ChainedCalendarEventPart(event1, Collections.singletonList(
                        new ChainedCalendarEventPart(event2, Collections.singletonList(
                                new ChainedCalendarEventPart(event3, Collections.emptyList())
                        ))))),
                this.subject.enlarge(Arrays.asList(
                        Collections.singletonList(event1),
                        Collections.singletonList(event2),
                        Collections.singletonList(event3))));
    }

    @Test
    /*
    |-| |-|    |-|  |-|
    |_| | |    |_|->| |
        | | =>      | |
    |-| | |    |-|  | |
    |_| |_|    |_|->|_|
     */
    public void twoChainsNoResizing() {
        final CalendarEventPart event1 = newEvent("event1", 8, 9);
        final CalendarEventPart event2 = newEvent("event2", 10, 11);
        final CalendarEventPart event3 = newEvent("event3", 8, 11);

        final ChainedCalendarEventPart chainedEventPart3 =
                new ChainedCalendarEventPart(event3, Collections.emptyList());
        Assert.assertEquals(Arrays.asList(
                new ChainedCalendarEventPart(event1, Collections.singletonList(chainedEventPart3)),
                new ChainedCalendarEventPart(event2, Collections.singletonList(chainedEventPart3))),
                this.subject.enlarge(Arrays.asList(
                        Arrays.asList(event1, event2),
                        Collections.singletonList(event3))));

    }

    @Test
    /*
    |-|         |------|
    |_|         |______|
            =>
    |-| |-|     |-|  |-|
    |_| |_|     |_|->|_|
     */
    public void twoChainsResizingFirstColumnEventWithNoConflicts() {
        final CalendarEventPart event1 = newEvent("event1", 8, 9);
        final CalendarEventPart event2 = newEvent("event2", 10, 11);
        final CalendarEventPart event3 = newEvent("event3", 10, 11);

        Assert.assertEquals(Arrays.asList(
                new ChainedCalendarEventPart(event1, Collections.emptyList(),
                        new ChainedCalendarEventPart.ResizeParameters(0, 2)),
                new ChainedCalendarEventPart(event2, Collections.singletonList(
                        new ChainedCalendarEventPart(event3, Collections.emptyList())))),
                this.subject.enlarge(Arrays.asList(
                        Arrays.asList(event1, event2),
                        Collections.singletonList(event3))));
    }

    @Test
    /*
    |-|     |-|     |------|  |-|
    |_|     | |     |______|->| |
            | | =>            | |
    |-| |-| | |     |-|  |-|  | |
    |_| |_| |_|     |_|->|_|->|_|
     */
    public void twoChainsResizingFirstColumnEventWithConflict() {
        final CalendarEventPart event1 = newEvent("event1", 8, 9);
        final CalendarEventPart event2 = newEvent("event2", 10, 11);
        final CalendarEventPart event3 = newEvent("event3", 10, 11);
        final CalendarEventPart event4 = newEvent("event4", 8, 11);

        final ChainedCalendarEventPart chainedEventPart4 =
                new ChainedCalendarEventPart(event4, Collections.emptyList());
        Assert.assertEquals(Arrays.asList(
                new ChainedCalendarEventPart(event1, Collections.singletonList(chainedEventPart4),
                        new ChainedCalendarEventPart.ResizeParameters(0, 2)),
                new ChainedCalendarEventPart(event2, Collections.singletonList(
                        new ChainedCalendarEventPart(event3, Collections.singletonList(
                                chainedEventPart4))))),
                this.subject.enlarge(Arrays.asList(
                        Arrays.asList(event1, event2),
                        Collections.singletonList(event3),
                        Collections.singletonList(event4))));
    }

    @Test
    /*
    |-|         |-|     |-----------|  |-|
    |_|         | |     |___________|->| |
                | | =>                 | |
    |-| |-| |-| | |     |-|  |-|  |-|  | |
    |_| |_| |_| |_|     |_|->|_|->|_|->|_|
    */
    public void twoChainsFirstColumneEventBigResizing() {
        final CalendarEventPart event1 = newEvent("event1", 8, 9);
        final CalendarEventPart event2 = newEvent("event2", 10, 11);
        final CalendarEventPart event3 = newEvent("event3", 10, 11);
        final CalendarEventPart event4 = newEvent("event4", 10, 11);
        final CalendarEventPart event5 = newEvent("event5", 8, 11);

        final ChainedCalendarEventPart chainedEventPart5 =
                new ChainedCalendarEventPart(event5, Collections.emptyList());
        Assert.assertEquals(Arrays.asList(
                new ChainedCalendarEventPart(event1, Collections.singletonList(chainedEventPart5),
                        new ChainedCalendarEventPart.ResizeParameters(0, 3)),
                new ChainedCalendarEventPart(event2, Collections.singletonList(
                        new ChainedCalendarEventPart(event3, Collections.singletonList(
                                new ChainedCalendarEventPart(event4, Collections.singletonList(
                                        chainedEventPart5))))))),
                this.subject.enlarge(Arrays.asList(
                        Arrays.asList(event1, event2),
                        Collections.singletonList(event3),
                        Collections.singletonList(event4),
                        Collections.singletonList(event5))));
    }

    @Test
    /*
    |1|     |6|         |1-----|  |6-----|
    |_|     | |         |______|->|      |
            | |                   |      |
    |2| |4| | |         |2|  |4|  |      |
    | | |_| |_|         | |->|_|->|______|
    | |             =>  | |
    | | |5| |7| |8|     | |  |5|  |7|  |8|
    |_| |_| |_| |_|     |_|->|_|->|_|->|_|

    |3|                 |3---------------|
    |_|                 |________________|
     */
    public void manyResizableEvents() {
        final CalendarEventPart event1 = newEvent("event1", 8, 9);
        final CalendarEventPart event2 = newEvent("event2", 10, 13);
        final CalendarEventPart event3 = newEvent("event3", 14, 15);
        final CalendarEventPart event4 = newEvent("event4", 10, 11);
        final CalendarEventPart event5 = newEvent("event5", 12, 13);
        final CalendarEventPart event6 = newEvent("event6", 8, 11);
        final CalendarEventPart event7 = newEvent("event7", 12, 13);
        final CalendarEventPart event8 = newEvent("event8", 12, 13);

        final ChainedCalendarEventPart chainedEventPart6 =
                new ChainedCalendarEventPart(event6, Collections.emptyList(),
                        new ChainedCalendarEventPart.ResizeParameters(2, 4));

        Assert.assertEquals(Arrays.asList(
                new ChainedCalendarEventPart(event1, Collections.singletonList(chainedEventPart6),
                        new ChainedCalendarEventPart.ResizeParameters(0, 2)),
                new ChainedCalendarEventPart(event2, Arrays.asList(
                        new ChainedCalendarEventPart(event4, Collections.singletonList(
                                chainedEventPart6
                        )),
                        new ChainedCalendarEventPart(event5, Collections.singletonList(
                                new ChainedCalendarEventPart(event7, Collections.singletonList(
                                        new ChainedCalendarEventPart(event8,
                                                Collections.emptyList())
                                ))
                        ))
                )),
                new ChainedCalendarEventPart(event3, Collections.emptyList(),
                        new ChainedCalendarEventPart.ResizeParameters(0, 4))),
                this.subject.enlarge(
                        Arrays.asList(Arrays.asList(event1, event2, event3),
                                Arrays.asList(event4, event5),
                                Arrays.asList(event6, event7),
                                Collections.singletonList(event8)
                        )));
    }
}