package com.example.radle.todo_calendar2.calendarView;

import android.graphics.Rect;

import com.example.radle.todo_calendar2.dto.CalendarEvent;
import com.example.radle.todo_calendar2.dto.CalendarEventPart;

import java.time.LocalDateTime;
import java.util.Objects;

public class CalendarEventPartWithBounds extends CalendarEventPart {

    private final Rect rect;

    public CalendarEventPartWithBounds(String title, LocalDateTime startTime, LocalDateTime endTime, CalendarEvent calendarEvent, Rect rect) {
        super(title, startTime, endTime, calendarEvent);
        this.rect = rect;
    }

    public Rect getRect() {
        return this.rect;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CalendarEventPartWithBounds that = (CalendarEventPartWithBounds) o;
        return Objects.equals(rect, that.rect);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), rect.left, rect.top, rect.right, rect.bottom);
    }
}
