package com.example.radle.todo_calendar2.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class CalendarSelection {
    private final float x;
    private final float y;
    private final LocalDateTime selectedTime;
    private final float left;
    private final float top;
    private final float right;
    private final float bottom;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    private CalendarSelection(float x, float y, LocalDateTime selectedTime, float left, float top, float right, float bottom,
                             LocalDateTime startTime, LocalDateTime endTime) {
        this.x = x;
        this.y = y;
        this.selectedTime = selectedTime;
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.startTime = startTime;
        this.endTime = endTime;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalendarSelection that = (CalendarSelection) o;
        return Float.compare(that.x, x) == 0 &&
                Float.compare(that.left, left) == 0 &&
                Float.compare(that.top, top) == 0 &&
                Float.compare(that.right, right) == 0 &&
                Float.compare(that.bottom, bottom) == 0 &&
                Objects.equals(selectedTime, that.selectedTime) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, selectedTime, left, top, right, bottom, startTime, endTime);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public static class Builder {

        private float left;
        private float top;
        private float right;
        private float bottom;
        private float x;
        private float y;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private LocalDateTime selectedTime;

        public Builder withLeft(float left) {
            this.left = left;
            return this;
        }

        public Builder withTop(float top) {
            this.top = top;
            return this;
        }

        public Builder withRight(float right) {
            this.right = right;
            return this;
        }

        public Builder withBottom(float bottom) {
            this.bottom = bottom;
            return this;
        }

        public Builder withX(float x) {
            this.x = x;
            return this;
        }

        public Builder withY(float y) {
            this.y = y;
            return this;
        }

        public Builder withStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder withEndTime(LocalDateTime endTime) {
            this.endTime = endTime;
            return this;
        }

        public Builder withSelectedTime(LocalDateTime selectedTime) {
            this.selectedTime = selectedTime;
            return this;
        }

        public CalendarSelection build() {
            return new CalendarSelection(x, y, selectedTime, this.left, this.top, this.right, this.bottom, this.startTime, this.endTime);
        }
    }
}
