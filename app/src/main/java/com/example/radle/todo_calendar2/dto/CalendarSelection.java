package com.example.radle.todo_calendar2.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class CalendarSelection {
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public CalendarSelection(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static CalendarSelection halfHourLater() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime halfHourLater;
        if (now.getMinute() < 30) {
            halfHourLater = now.withMinute(30).withSecond(0);
        } else {
            halfHourLater = now.plusHours(1).withMinute(0).withSecond(0);
        }
        return new CalendarSelection.Builder()
                .withStartTime(halfHourLater)
                .withEndTime(halfHourLater.plusMinutes(30))
                .build();
    }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalendarSelection that = (CalendarSelection) o;
        return Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime);
    }

    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    public static class Builder {
        private LocalDateTime startTime;
        private LocalDateTime endTime;

        public CalendarSelection.Builder withStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public CalendarSelection.Builder withEndTime(LocalDateTime endTime) {
            this.endTime = endTime;
            return this;
        }

        public CalendarSelection build() {
            return new CalendarSelection(startTime, endTime);
        }
    }
}
