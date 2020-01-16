package com.example.radle.todo_calendar2.calendarView.tools.events;

import com.example.radle.todo_calendar2.calendarView.dto.CalendarEvent;
import com.example.radle.todo_calendar2.calendarView.dto.CalendarEventPart;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

abstract class AbstractEventsComposer {
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public AbstractEventsComposer(final LocalDateTime startTime) {
        this.startTime = startTime;
        this.endTime = evaluateEndTime(startTime);
    }

    abstract LocalDateTime evaluateEndTime(LocalDateTime startTime);

    public List<CalendarEventPart> getEventsParts(final List<CalendarEvent> calendarEvents) {
        final List<CalendarEventPart> eventsParts = new ArrayList<>();
        for (final CalendarEvent calendarEvent : calendarEvents) {
            final Optional<CalendarEventPart> eventPart =
                    getCalendarEventPart(calendarEvent);
            eventPart.ifPresent(eventsParts::add);
        }
        return eventsParts;
    }

    private Optional<CalendarEventPart> getCalendarEventPart(final CalendarEvent calendarEvent) {
        final Optional<LocalDateTime> startTime = getStartTime(calendarEvent);
        final Optional<LocalDateTime> endTime = getEndTime(calendarEvent);
        if (startTime.isPresent() && endTime.isPresent())
            return Optional.of(new CalendarEventPart(calendarEvent.getTitle(), startTime.get(),
                    endTime.get(), calendarEvent));
        return Optional.empty();
    }

    private Optional<LocalDateTime> getStartTime(final CalendarEvent calendarEvent) {
        if (!calendarEvent.getStartTime().isBefore(this.endTime))
            return Optional.empty();
        return Optional.of(calendarEvent.getStartTime().isBefore(this.startTime) ?
                this.startTime : calendarEvent.getStartTime());
    }

    private Optional<LocalDateTime> getEndTime(final CalendarEvent calendarEvent) {
        if (!calendarEvent.getEndTime().isAfter(this.startTime))
            return Optional.empty();
        return Optional.of(calendarEvent.getEndTime().isBefore(this.endTime) ?
                calendarEvent.getEndTime() : this.endTime);
    }
}
