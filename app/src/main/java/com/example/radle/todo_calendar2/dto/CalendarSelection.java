package com.example.radle.todo_calendar2.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class CalendarSelection {
    private final float left;
    private final float top;
    private final float right;
    private final float bottom;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public
    CalendarSelection(float left, float top, float right, float bottom,
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

    public float getLeft() {
        return left;
    }

    public float getTop() {
        return top;
    }

    public float getRight() {
        return right;
    }

    public float getBottom() {
        return bottom;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}
