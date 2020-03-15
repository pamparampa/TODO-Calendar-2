package com.example.radle.todo_calendar2.calendarView.tools.events;

import com.example.radle.todo_calendar2.calendarView.dto.CalendarEventPartWithWidth;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.time.LocalTime;
import java.util.Set;

public class DayEventsDivider {
    public Multimap<Integer, CalendarEventPartWithWidth> divide(final Set<CalendarEventPartWithWidth> eventParts) {
        final Multimap<Integer, CalendarEventPartWithWidth> dividedEventParts =
                HashMultimap.create();
        for (final CalendarEventPartWithWidth eventPart : eventParts) {
            dividedEventParts.putAll(divideSingleEventPart(eventPart));

        }
        return dividedEventParts;
    }

    private Multimap<Integer, CalendarEventPartWithWidth> divideSingleEventPart(final CalendarEventPartWithWidth eventPart) {
        final Multimap<Integer, CalendarEventPartWithWidth> dividedEventParts =
                HashMultimap.create();
        for (LocalTime time = LocalTime.of(eventPart.getStartTime().getHour(), 0);
             time.isBefore(eventPart.getEndTime().toLocalTime());
             time = time.plusHours(1)) {
            dividedEventParts.put(time.getHour(), eventPart.withNewTime(
                    getMaxStartTime(eventPart, time), getMinEndTime(eventPart, time)));
        }
        return dividedEventParts;
    }

    private LocalTime getMaxStartTime
            (final CalendarEventPartWithWidth eventPart,
             final LocalTime time) {
        return eventPart.getStartTime().toLocalTime().isAfter(time) ?
                eventPart.getStartTime().toLocalTime() : time;
    }

    private LocalTime getMinEndTime(final CalendarEventPartWithWidth eventPart,
                                    final LocalTime time) {
        return eventPart.getEndTime().toLocalTime().isBefore(time.plusHours(1)) ?
                eventPart.getEndTime().toLocalTime() : time.plusHours(1);
    }
}
