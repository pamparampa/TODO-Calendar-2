package com.example.radle.todo_calendar2.calendarView.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class CalendarEventPart extends CalendarEvent {
    private final CalendarEvent calendarEvent;

    public CalendarEventPart(final String title, final LocalDateTime startTime,
                             final LocalDateTime endTime, final CalendarEvent calendarEvent) {
        super(title, startTime, endTime);
        this.calendarEvent = calendarEvent;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof CalendarEventPart)) return false;
        final CalendarEventPart that = (CalendarEventPart) o;
        return super.equals(o) &&
                Objects.equals(this.calendarEvent, that.calendarEvent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getStartTime(), getEndTime(), this.calendarEvent);
    }

    @Override
    public String toString() {
        return "CalendarEventPart{" +
                "title='" + getTitle() + '\'' +
                ", startTime=" + getStartTime() +
                ", endTime=" + getEndTime() +
                ", calendarEvent=" + this.calendarEvent +
                '}';
    }

    public CalendarEventPartWithWidth withWidth(final float left, final float right,
                                                final int divider) {
        return new CalendarEventPartWithWidth(this.calendarEvent, getTitle(), getStartTime(),
                getEndTime(), left, right, divider);
    }

    @Override
    public CalendarEvent getCalendarEvent() {
        return this.calendarEvent;
    }

    public boolean hasConflictWith(final CalendarEventPart calendarEventPart) {
        return (getStartTime().isBefore(calendarEventPart.getEndTime()) && getEndTime()
                .isAfter(calendarEventPart.getStartTime()));
    }

    @Override
    public int getColor() {
        return this.calendarEvent.getColor();
    }
}
