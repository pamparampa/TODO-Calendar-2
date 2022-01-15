package com.example.radle.todo_calendar2.calendarView.tools.events;

import android.graphics.Rect;

import com.example.radle.todo_calendar2.calendarView.CalendarEventPartWithBounds;
import com.example.radle.todo_calendar2.dto.CalendarEvent;
import com.example.radle.todo_calendar2.dto.VisibleCalendarSelection;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class CalendarEventSearcher {
    private Set<CalendarEventPartWithBounds> eventParts = new HashSet<>();

    public Optional<CalendarEvent> getEvent(VisibleCalendarSelection currentSelection) {
        for (CalendarEventPartWithBounds eventPart : eventParts) {
            if (isSelected(eventPart, currentSelection)) {
                return Optional.of(eventPart);
            }
        }
        return Optional.empty();
    }

    private boolean isSelected(CalendarEventPartWithBounds event, VisibleCalendarSelection currentSelection) {
        Rect rect = event.getRect();
        return rect.left <= currentSelection.getX()
                && rect.top <= currentSelection.getY()
                && rect.right >= currentSelection.getX()
                && rect.bottom >= currentSelection.getY();
    }

    public void putEvents(Set<CalendarEventPartWithBounds> eventsWithBounds) {
        this.eventParts = eventsWithBounds;
    }
}
