package com.example.radle.todo_calendar2.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class CalendarSelection {
    private final int left;
    private final int top;
    private final int right;
    private final int bottom;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public
    CalendarSelection(int left, int top, int right, int bottom,
                      LocalDateTime startTime, LocalDateTime endTime) {

        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.left, this.top, this.right, this.bottom, this.startTime, this.endTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalendarSelection that = (CalendarSelection) o;
        return left == that.left &&
                top == that.top &&
                right == that.right &&
                bottom == that.bottom &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime);
    }
}
