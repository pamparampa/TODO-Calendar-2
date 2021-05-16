package com.example.radle.todo_calendar2.calendarView.tools.events;


import com.example.radle.todo_calendar2.dto.CalendarEventPartWithWidth;
import com.google.common.collect.HashMultimap;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static com.example.radle.todo_calendar2.calendarView.tools.events.CalendarEventsCreateUtil.newEvent;

public class DayEventsDividerTest {
    DayEventsDivider subject = new DayEventsDivider();

    @Test
    public void divide_shouldReturnEmptyMap_whenThereAreNoEvents() {
        Assert.assertEquals(HashMultimap.create(), this.subject.divide(Collections.emptySet()));
    }

    @Test
    public void divide_shouldReturnMapOfOneEventPart_whenThereIsOneEventFitInOneHour() {
        final CalendarEventPartWithWidth eventPart = newEvent("event1", 2, 3)
                .withWidth(0, 1, 1);
        final HashMultimap<Integer, CalendarEventPartWithWidth> expectedMap = HashMultimap.create();
        expectedMap.put(2, eventPart);
        Assert.assertEquals(expectedMap, this.subject.divide(Collections.singleton(eventPart)));
    }

    @Test
    public void divide_shouldReturnMapOfTwoEventParts_whenThereIsOneEventFitInTwoEventParts() {
        final CalendarEventPartWithWidth eventPart = newEvent("event1", 4, 6)
                .withWidth(0, 1, 1);
        final HashMultimap<Integer, CalendarEventPartWithWidth> expectedMap = HashMultimap.create();
        expectedMap.put(4, eventPart.withNewTime(LocalTime.of(4, 0), LocalTime.of(5, 0)));
        expectedMap.put(5, eventPart.withNewTime(LocalTime.of(5, 0), LocalTime.of(6, 0)));
        Assert.assertEquals(expectedMap, this.subject.divide(Collections.singleton(eventPart)));
    }

    @Test
    public void divide_shouldReturnMapOfFewEvents_whenEventTakesBetweenFewHours() {
        final CalendarEventPartWithWidth eventPart = newEvent("event1",
                LocalTime.of(12, 35), LocalTime.of(14, 10))
                .withWidth(0, 1, 1);
        final HashMultimap<Integer, CalendarEventPartWithWidth> expectedMap = HashMultimap.create();
        expectedMap.put(12, eventPart.withNewTime(LocalTime.of(12, 35), LocalTime.of(13, 0)));
        expectedMap.put(13, eventPart.withNewTime(LocalTime.of(13, 0), LocalTime.of(14, 0)));
        expectedMap.put(14, eventPart.withNewTime(LocalTime.of(14, 0), LocalTime.of(14, 10)));
        Assert.assertEquals(expectedMap, this.subject.divide(Collections.singleton(eventPart)));
    }

    @Test
    public void divide_shouldReturnMapOfFewEvents_whenThereAreFewEvents() {
        final CalendarEventPartWithWidth eventPart1 = newEvent("event1",
                LocalTime.of(1, 0), LocalTime.of(4, 20))
                .withWidth(0, 1, 2);
        final CalendarEventPartWithWidth eventPart2 = newEvent("event2",
                LocalTime.of(4, 0), LocalTime.of(5, 30))
                .withWidth(1, 2, 2);

        final HashMultimap<Integer, CalendarEventPartWithWidth> expectedMap = HashMultimap.create();
        expectedMap.put(1, eventPart1.withNewTime(LocalTime.of(1, 0), LocalTime.of(2, 0)));
        expectedMap.put(2, eventPart1.withNewTime(LocalTime.of(2, 0), LocalTime.of(3, 0)));
        expectedMap.put(3, eventPart1.withNewTime(LocalTime.of(3, 0), LocalTime.of(4, 0)));
        expectedMap.put(4, eventPart1.withNewTime(LocalTime.of(4, 0), LocalTime.of(4, 20)));
        expectedMap.put(4, eventPart2.withNewTime(LocalTime.of(4, 0), LocalTime.of(5, 0)));
        expectedMap.put(5, eventPart2.withNewTime(LocalTime.of(5, 0), LocalTime.of(5, 30)));

        Assert.assertEquals(expectedMap,
                this.subject.divide(new HashSet<>(Arrays.asList(eventPart1, eventPart2))));
    }
}