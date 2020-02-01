package com.example.radle.todo_calendar2.calendarView.tools.events.conflicts;

import com.example.radle.todo_calendar2.calendarView.dto.CalendarEventPart;
import com.example.radle.todo_calendar2.calendarView.dto.CalendarEventPartWithWidth;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.internal.util.collections.Sets;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import static com.example.radle.todo_calendar2.calendarView.tools.events.conflicts.CalendarEventsCreateUtil.newEvent;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class DayEventsAveragingTest {

    private final DayEventsAveraging subject = new DayEventsAveraging();

    @Test
    public void average_shouldReturnEmptySet_whenThereAreNoEvents() {
        Assert.assertEquals(Collections.emptySet(), this.subject.average(emptyList()));
    }


    @Test
    /*
    |1|  |3|  |4|   |1| |3| |4|
    |_|->| |->|_|   |_| | | |_|
         | |     =>     | |
    |2|  | |        |2| | |
    |_|->|_|        |_| |_|
     */
    public void average_shouldReturnNotResizedEvents_whenNoneOfThemWasEnlarged() {
        final CalendarEventPart event1 = newEvent("event1", 8, 9);
        final CalendarEventPart event2 = newEvent("event2", 10, 11);
        final CalendarEventPart event3 = newEvent("event3", 8, 11);
        final CalendarEventPart event4 = newEvent("event4", 8, 9);

        final ChainedCalendarEventPart chainedEvent3 =
                new ChainedCalendarEventPart(event3, 1, 2,
                        singletonList(new ChainedCalendarEventPart(event4, 2, 3,
                                emptyList())));
        Assert.assertEquals(
                Sets.newSet(new CalendarEventPartWithWidth(event1, 0, 1),
                        new CalendarEventPartWithWidth(event2, 0, 1),
                        new CalendarEventPartWithWidth(event3, 1, 2),
                        new CalendarEventPartWithWidth(event4, 2, 3)),
                this.subject.average(
                        Arrays.asList(
                                new ChainedCalendarEventPart(event1, 0, 1,
                                        singletonList(chainedEvent3)),
                                new ChainedCalendarEventPart(event2, 0, 1,
                                        singletonList(chainedEvent3)))));
    }

    @Test
    /*
    |1|  |3-----|   |1---|3---|
    |_|->|______|   |____|____|
                 =>
    |2|  |4|  |5|   |2| |4| |5|
    |_|->|_|->|_|   |_| |_| |_|
     */
    public void average_shouldReturnSizeAveragedEvents_whenFirstOfThemHadConflictOnlyWithEnlargedEvent() {
        final CalendarEventPart event1 = newEvent("event1", 8, 9);
        final CalendarEventPart event2 = newEvent("event2", 10, 11);
        final CalendarEventPart event3 = newEvent("event3", 8, 9);
        final CalendarEventPart event4 = newEvent("event4", 10, 11);
        final CalendarEventPart event5 = newEvent("event5", 10, 11);

        Assert.assertEquals(
                Sets.newSet(
                        new CalendarEventPartWithWidth(event1, 0, 1.5f),
                        new CalendarEventPartWithWidth(event2, 0, 1),
                        new CalendarEventPartWithWidth(event3, 1.5f, 3),
                        new CalendarEventPartWithWidth(event4, 1, 2),
                        new CalendarEventPartWithWidth(event5, 2, 3)),
                this.subject.average(Arrays.asList(
                        new ChainedCalendarEventPart(event1, 0, 1, singletonList(
                                new ChainedCalendarEventPart(event3, 1, 3,
                                        emptyList()))),
                        new ChainedCalendarEventPart(event2, 0, 1, singletonList(
                                new ChainedCalendarEventPart(event4, 1, 2,
                                        singletonList(
                                                new ChainedCalendarEventPart(event5, 2, 3,
                                                        emptyList()))))))));
    }

    @Test
    /*
    |1|  |2-----|   |1| |2----|
    | |->|______|   | | |_____|
    | |           =>| |
    | |  |3|  |4|   | | |3| |4|
    |_|->|_|->|_|   |_| |_| |_|
    */
    public void average_shouldReturnNotSizeAveragedEvents_whenEventHasConflictOneEnlargedAndOneNotEnlargedEvent() {
        final CalendarEventPart event1 = newEvent("event1", 8, 11);
        final CalendarEventPart event2 = newEvent("event2", 8, 9);
        final CalendarEventPart event3 = newEvent("event3", 10, 11);
        final CalendarEventPart event4 = newEvent("event4", 10, 11);

        Assert.assertEquals(
                Sets.newSet(
                        new CalendarEventPartWithWidth(event1, 0, 1),
                        new CalendarEventPartWithWidth(event2, 1, 3),
                        new CalendarEventPartWithWidth(event3, 1, 2),
                        new CalendarEventPartWithWidth(event4, 2, 3)),
                this.subject.average(singletonList(
                        new ChainedCalendarEventPart(event1, 0, 1, Arrays.asList(
                                new ChainedCalendarEventPart(event2, 1, 3, emptyList()),
                                new ChainedCalendarEventPart(event3, 1, 2,
                                        singletonList(
                                                new ChainedCalendarEventPart(event4, 2, 3,
                                                        emptyList()))))))));
    }

    @Test
    /*
    |1----------|  |8-----|     |1--------|  |8---|
    |___________|->|      |     |_________|  |    |
                   |      |                  |    |
    |2|  |4|  |6|  |      | =>  |2| |4| |6---|    |
    |_|->|_|->|_|->|______|     |_| |_| |____|____|

    |3|  |5|  |7|  |9|  |10     |3| |5| |7| |9| |10
    |_|->|_|->|_|->|_|->|_|     |_| |_| |_| |_| |_|
     */
    public void average_shouldReturnNotSizeAveragedEvents_WhenRightEventIsEnlargedLessThanLeftEvent() {
        final CalendarEventPart event1 = newEvent("event1", 8, 9);
        final CalendarEventPart event2 = newEvent("event2", 10, 11);
        final CalendarEventPart event3 = newEvent("event3", 12, 13);
        final CalendarEventPart event4 = newEvent("event4", 10, 11);
        final CalendarEventPart event5 = newEvent("event5", 12, 13);
        final CalendarEventPart event6 = newEvent("event6", 10, 11);
        final CalendarEventPart event7 = newEvent("event7", 12, 13);
        final CalendarEventPart event8 = newEvent("event8", 8, 11);
        final CalendarEventPart event9 = newEvent("event9", 12, 13);
        final CalendarEventPart event10 = newEvent("event10", 12, 13);

        final ChainedCalendarEventPart chainedEvent8 = new ChainedCalendarEventPart(event8, 3, 5,
                emptyList());

        final Set<CalendarEventPartWithWidth> result = this.subject.average(Arrays.asList(
                new ChainedCalendarEventPart(event1, 0, 3, singletonList(chainedEvent8)),
                new ChainedCalendarEventPart(event2, 0, 1, singletonList(
                        new ChainedCalendarEventPart(event4, 1, 2, singletonList(
                                new ChainedCalendarEventPart(event6, 2, 3, singletonList(
                                        chainedEvent8)))))),
                new ChainedCalendarEventPart(event3, 0, 1, singletonList(
                        new ChainedCalendarEventPart(event5, 1, 2, singletonList(
                                new ChainedCalendarEventPart(event7, 2, 3, singletonList(
                                        new ChainedCalendarEventPart(event9, 3, 4, singletonList(
                                                new ChainedCalendarEventPart(event10, 4, 5,
                                                        emptyList())))))))))));
        Assert.assertEquals(
                Sets.newSet(
                        new CalendarEventPartWithWidth(event1, 0, 3),
                        new CalendarEventPartWithWidth(event2, 0, 1),
                        new CalendarEventPartWithWidth(event3, 0, 1),
                        new CalendarEventPartWithWidth(event4, 1, 2),
                        new CalendarEventPartWithWidth(event5, 1, 2),
                        new CalendarEventPartWithWidth(event6, 2, 3.5f),
                        new CalendarEventPartWithWidth(event7, 2, 3),
                        new CalendarEventPartWithWidth(event8, 3.5f, 5),
                        new CalendarEventPartWithWidth(event9, 3, 4),
                        new CalendarEventPartWithWidth(event10, 4, 5)),
                result);

    }

    /*
    |1|  |3----------|  |1---|3-------|
    | |->|___________|  |    |________|
    | |                 |    |
    | |  |4-----|  |8|  |    |4---| |8|
    | |->|______|->|_|  |    |____| |_|
    | |               =>|    |
    | |  |5----------|  |    |5-------|
    |_|->|___________|  |____|________|

    |2|  |6|  |7|  |9|  |2| |6| |7| |9|
    |_|->|_|->|_|->|_|  |_| |_| |_| |_|
     */
    @Test
    public void average_shouldReturnEventEnlargedByMinimumSize_whenFewEnlargedEventsOnTheRight() {
        final CalendarEventPart event1 = newEvent("event1", 8, 15);
        final CalendarEventPart event2 = newEvent("event2", 17, 19);
        final CalendarEventPart event3 = newEvent("event3", 8, 9);
        final CalendarEventPart event4 = newEvent("event4", 10, 11);
        final CalendarEventPart event5 = newEvent("event5", 13, 15);
        final CalendarEventPart event6 = newEvent("event6", 17, 19);
        final CalendarEventPart event7 = newEvent("event7", 17, 19);
        final CalendarEventPart event8 = newEvent("event8", 10, 11);
        final CalendarEventPart event9 = newEvent("event9", 17, 19);

        Assert.assertEquals(
                Sets.newSet(
                        new CalendarEventPartWithWidth(event1, 0, 1.5f),
                        new CalendarEventPartWithWidth(event2, 0, 1),
                        new CalendarEventPartWithWidth(event3, 1.5f, 4),
                        new CalendarEventPartWithWidth(event4, 1.5f, 3),
                        new CalendarEventPartWithWidth(event5, 1.5f, 4),
                        new CalendarEventPartWithWidth(event6, 1, 2),
                        new CalendarEventPartWithWidth(event7, 2, 3),
                        new CalendarEventPartWithWidth(event8, 3, 4),
                        new CalendarEventPartWithWidth(event9, 3, 4)),
                this.subject.average(Arrays.asList(
                        new ChainedCalendarEventPart(event1, 0, 1, Arrays.asList(
                                new ChainedCalendarEventPart(event3, 1, 4, emptyList()),
                                new ChainedCalendarEventPart(event4, 1, 3, singletonList(
                                        new ChainedCalendarEventPart(event8, 3, 4,
                                                emptyList()))),
                                new ChainedCalendarEventPart(event5, 1, 4, emptyList())
                        )),
                        new ChainedCalendarEventPart(event2, 0, 1, singletonList(
                                new ChainedCalendarEventPart(event6, 1, 2, singletonList(
                                        new ChainedCalendarEventPart(event7, 2, 3, singletonList(
                                                new ChainedCalendarEventPart(event9, 3, 4,
                                                        emptyList()))))))))));
    }

    /*
    |1|  |4-----|  |8-----|     |1---|4---| |8----|
    |_|->|______|->|      |     |____|____| |     |
                   |      |                 |     |
    |2|  |5|  |7|  |      |     |2| |5| |7| |     |
    |_|->|_|->| |->|______| =>  |_| |_| | | |_____|
              | |                       | |
    |3|  |6|  | |  |9|  |10     |3| |6| | | |9| |10
    |_|->|_|->|_|->|_|->|_|     |_| |_| |_| |_| |_|
     */
    @Test
    public void averagingManyEventsInBreadthAtTheTimeIsNotSupported() {
        final CalendarEventPart event1 = newEvent("event1", 8, 9);
        final CalendarEventPart event2 = newEvent("event2", 10, 11);
        final CalendarEventPart event3 = newEvent("event3", 12, 13);
        final CalendarEventPart event4 = newEvent("evnet4", 8, 9);
        final CalendarEventPart event5 = newEvent("event5", 10, 11);
        final CalendarEventPart event6 = newEvent("event6", 12, 13);
        final CalendarEventPart event7 = newEvent("event7", 10, 13);
        final CalendarEventPart event8 = newEvent("event8", 8, 11);
        final CalendarEventPart event9 = newEvent("event9", 12, 13);
        final CalendarEventPart event10 = newEvent("event10", 12, 13);

        final ChainedCalendarEventPart chainedEventPart8 =
                new ChainedCalendarEventPart(event8, 3, 5, emptyList());
        final ChainedCalendarEventPart chainedEventPart7 =
                new ChainedCalendarEventPart(event7, 2, 3, Arrays.asList(
                        chainedEventPart8,
                        new ChainedCalendarEventPart(event9, 3, 4, singletonList(
                                new ChainedCalendarEventPart(event10, 4, 5,
                                        emptyList())))));
        final Set<CalendarEventPartWithWidth> result = this.subject.average(Arrays.asList(
                new ChainedCalendarEventPart(event1, 0, 1, singletonList(
                        new ChainedCalendarEventPart(event4, 1, 3, singletonList(
                                chainedEventPart8)))),
                new ChainedCalendarEventPart(event2, 0, 1, singletonList(
                        new ChainedCalendarEventPart(event5, 1, 2, singletonList(
                                chainedEventPart7)))),
                new ChainedCalendarEventPart(event3, 0, 1, singletonList(
                        new ChainedCalendarEventPart(event6, 1, 2, singletonList(
                                chainedEventPart7))))));

        Assert.assertEquals(
                Sets.newSet(
                        new CalendarEventPartWithWidth(event1, 0, 1.5f),
                        new CalendarEventPartWithWidth(event2, 0, 1),
                        new CalendarEventPartWithWidth(event3, 0, 1),
                        new CalendarEventPartWithWidth(event4, 1.5f, 3),
                        new CalendarEventPartWithWidth(event5, 1, 2),
                        new CalendarEventPartWithWidth(event6, 1, 2),
                        new CalendarEventPartWithWidth(event7, 2, 3),
                        new CalendarEventPartWithWidth(event8, 3, 5),
                        new CalendarEventPartWithWidth(event9, 3, 4),
                        new CalendarEventPartWithWidth(event10, 4, 5)),
                result);
    }
}