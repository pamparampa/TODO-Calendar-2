package com.example.radle.todo_calendar2.calendarView.tools.events.conflicts;

import com.example.radle.todo_calendar2.calendarView.dto.CalendarEventPartWithWidth;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class DayEventsAveraging {

    private static final int MAX_WIDTH = 10;

    public Set<CalendarEventPartWithWidth> average(
            final List<ChainedCalendarEventPart> calendarEventsParts) {
        final Set<CalendarEventPartWithWidth> resizedEvents = new HashSet<>();
        for (final ChainedCalendarEventPart chainedCalendarEventPart : calendarEventsParts) {
            averageWidthsOfEvents(chainedCalendarEventPart);
            resizedEvents.add(chainedCalendarEventPart.unchain());

            final Set<CalendarEventPartWithWidth> calendarEventsOnTheRight =
                    average(chainedCalendarEventPart.getConflictedCalendarEventsOnTheRight());
            addNewVersionsOfEvents(resizedEvents, calendarEventsOnTheRight);
        }
        return resizedEvents;
    }

    private void addNewVersionsOfEvents(final Set<CalendarEventPartWithWidth> oldEvents,
                                        final Set<CalendarEventPartWithWidth> newEvents) {
        for (final CalendarEventPartWithWidth newEvent : newEvents) {
            final Iterator<CalendarEventPartWithWidth> oldEventsIterator = oldEvents.iterator();
            while (oldEventsIterator.hasNext()) {
                if (oldEventsIterator.next().withoutWidth().equals(newEvent.withoutWidth())) {
                    oldEventsIterator.remove();
                }
            }
            oldEvents.add(newEvent);
        }
    }

    private void averageWidthsOfEvents(final ChainedCalendarEventPart currentEventPart) {
        final float minimumWidthOfConflictedEvents = getMinimumWidthOfConflictedEvents(
                currentEventPart.getConflictedCalendarEventsOnTheRight());
        if (minimumWidthOfConflictedEvents >= currentEventPart.getWidth() + 1) {
            final float delta = currentEventPart.averageSize(minimumWidthOfConflictedEvents);
            for (final ChainedCalendarEventPart otherEventPart :
                    currentEventPart.getConflictedCalendarEventsOnTheRight())
                otherEventPart.trimLeft(delta);
        }
    }

    private float getMinimumWidthOfConflictedEvents(final List<ChainedCalendarEventPart> conflictedCalendarEventsOnTheRight) {
        if (conflictedCalendarEventsOnTheRight.size() == 0) return 0;
        float minimumWidth = MAX_WIDTH;
        for (final ChainedCalendarEventPart eventPart : conflictedCalendarEventsOnTheRight) {
            if (eventPart.getWidth() < minimumWidth) {
                minimumWidth = eventPart.getWidth();
            }
        }
        return minimumWidth;
    }
}
