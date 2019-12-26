package com.example.radle.todo_calendar2.calendarView.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class CalendarEventHourPart extends CalendarEventPart {
    private int titleShift;

    public CalendarEventHourPart(final String title, final int titleShift,
                                 final LocalDateTime startTime,
                                 final LocalDateTime endTime, final CalendarEvent calendarEvent) {
        super(title, startTime, endTime, calendarEvent);
        this.titleShift = titleShift;
    }

    public CalendarEventHourPart(final String title,
                                 final LocalDateTime startTime,
                                 final LocalDateTime endTime, final CalendarEvent calendarEvent) {
        super(title, startTime, endTime, calendarEvent);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof CalendarEventHourPart)) return false;
        if (!super.equals(o)) return false;
        final CalendarEventHourPart that = (CalendarEventHourPart) o;
        return this.titleShift == that.titleShift;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.titleShift);
    }

    @Override
    public String toString() {
        return "CalendarEventHourPart{" +
                "title='" + getTitle() + '\'' +
                ", startTime=" + getStartTime() +
                ", endTime=" + getEndTime() +
                ", calendarEvent=" + getCalendarEvent() +
                ", titleShift=" + this.titleShift +
                '}';
    }
}
