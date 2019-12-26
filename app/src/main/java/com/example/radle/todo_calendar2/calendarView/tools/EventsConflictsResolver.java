package com.example.radle.todo_calendar2.calendarView.tools;

import com.example.radle.todo_calendar2.calendarView.dto.CalendarEventDayPart;
import com.example.radle.todo_calendar2.calendarView.dto.CalendarEventPart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventsConflictsResolver {
    public List<CalendarEventDayPart> resolve(final List<CalendarEventPart> eventParts) {
        if (eventParts.isEmpty()) return Collections.emptyList();
        final CalendarEventPart calendarEventPart = eventParts.get(0);
        final List<CalendarEventDayPart> calendarEventDayParts = new ArrayList<>();
        calendarEventDayParts.add(
                new CalendarEventDayPart(calendarEventPart.getTitle(),
                        calendarEventPart.getStartTime(), calendarEventPart.getEndTime(),
                        calendarEventPart.getCalendarEvent()));
        return calendarEventDayParts;
    }
}
