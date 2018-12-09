package com.example.radle.todo_calendar2.calendarView.dto;

import java.time.LocalTime;
import java.util.Objects;

public class CalendarRowLabel {

    private final String text;
    private final LocalTime time;
    private final float textX;
    private final float textY;

    public CalendarRowLabel(final String text, final int textX, final int textY, final LocalTime
            time) {
        this.text = text;
        this.textX = textX;
        this.textY = textY;
        this.time = time;
    }

    @Override
    public int hashCode() {

        return Objects.hash(this.text, this.textX, this.textY, this.time);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof CalendarRowLabel)) return false;
        final CalendarRowLabel that = (CalendarRowLabel) o;
        return Float.compare(that.textX, this.textX) == 0 &&
                Float.compare(that.textY, this.textY) == 0 &&
                Objects.equals(this.text, that.text) &&
                Objects.equals(this.time, that.time);
    }

    @Override
    public String toString() {
        return "CalendarRowLabel{" +
                "text='" + this.text + '\'' +
                ", time=" + this.time +
                ", textX=" + this.textX +
                ", textY=" + this.textY +
                '}';
    }

    public String getText() {
        return this.text;
    }

    public float getTextX() {
        return this.textX;
    }

    public float getTextY() {
        return this.textY;
    }

}
