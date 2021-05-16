package com.example.radle.todo_calendar2.calendarView.tools.events;

import com.example.radle.todo_calendar2.calendarView.TimeNotAlignedException;
import com.example.radle.todo_calendar2.dto.CalendarEvent;
import com.example.radle.todo_calendar2.dto.CalendarEventPart;
import com.example.radle.todo_calendar2.dto.CalendarEventPartWithWidth;
import com.example.radle.todo_calendar2.dto.IdWithDataTime;
import com.example.radle.todo_calendar2.calendarView.tools.DateTimesCollector;
import com.example.radle.todo_calendar2.calendarView.tools.events.conflicts.DayEventsArrangement;
import com.example.radle.todo_calendar2.calendarView.tools.events.conflicts.DayEventsAveraging;
import com.example.radle.todo_calendar2.calendarView.tools.events.conflicts.DayEventsEnlargement;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EventsComposer {
    private final static int NUMBER_OF_DAYS = 7;
    private final WeekEventsComposer weekEventsComposer;
    private final List<DayEventsComposer> dayEventsComposers = new ArrayList<>(NUMBER_OF_DAYS);
    private final DayEventsArrangement eventsArrangement = new DayEventsArrangement();
    private final DayEventsEnlargement eventsEnlargement = new DayEventsEnlargement();
    private final DayEventsAveraging eventsAveraging = new DayEventsAveraging();

    public EventsComposer(final LocalDateTime firstDateTime) throws TimeNotAlignedException {
        this.weekEventsComposer = new WeekEventsComposer(firstDateTime);
        for (final IdWithDataTime idWithDataTime :
                new DateTimesCollector().collectForWeekRow(firstDateTime)) {
            this.dayEventsComposers.add(new DayEventsComposer(idWithDataTime.getDateTime()));
        }
    }

    public Set<CalendarEventPartWithWidth> compose(final List<CalendarEvent> events) {
        final Set<CalendarEventPartWithWidth>
                composedEvents = new HashSet<>();
        final List<CalendarEventPart> weekEventsParts =
                this.weekEventsComposer.getEventsParts(events);
        for (int i = 0; i < NUMBER_OF_DAYS; i++) {
            composedEvents.addAll(getCalendarEventsForDay(weekEventsParts, i));
        }
        return composedEvents;
    }

    private Set<CalendarEventPartWithWidth> getCalendarEventsForDay(final List<CalendarEventPart> weekEventsParts, final int dayNumber) {
        final DayEventsComposer dayEventsComposer = this.dayEventsComposers.get(dayNumber);
        final List<CalendarEventPart> dayEventsParts =
                dayEventsComposer.getEventsParts(weekEventsParts);
        return resolveConflicts(dayEventsParts);
    }

    private Set<CalendarEventPartWithWidth> resolveConflicts(final List<CalendarEventPart> dayEventsParts) {
        return this.eventsAveraging.average(
                this.eventsEnlargement.enlarge(
                        this.eventsArrangement.arrange(dayEventsParts)));
    }
}
