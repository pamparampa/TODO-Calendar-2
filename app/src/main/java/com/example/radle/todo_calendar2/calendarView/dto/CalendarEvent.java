package com.example.radle.todo_calendar2.calendarView.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class CalendarEvent {
    private final String title;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;


    public CalendarEvent(final String title, final LocalDateTime startTime,
                         final LocalDateTime endTime) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof CalendarEvent)) return false;
        final CalendarEvent that = (CalendarEvent) o;
        return Objects.equals(this.title, that.title) &&
                Objects.equals(this.startTime, that.startTime) &&
                Objects.equals(this.endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.title, this.startTime, this.endTime);
    }

    @Override
    public String toString() {
        return "CalendarEvent{" +
                "title='" + this.title + '\'' +
                ", startTime=" + this.startTime +
                ", endTime=" + this.endTime +
                '}';
    }

    public String getTitle() {
        return this.title;
    }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    public LocalDateTime getEndTime() {
        return this.endTime;
    }
}
