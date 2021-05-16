package com.example.radle.todo_calendar2.dto;

import android.graphics.Rect;

import java.time.LocalDateTime;
import java.util.Objects;

public class CalendarField {

    private final int id;
    private final Rect rect;
    private final LocalDateTime localDateTime;

    public CalendarField(final int id, final Rect rect, final LocalDateTime localDateTime) {
        this.id = id;
        this.rect = rect;
        this.localDateTime = localDateTime;
    }

    public Rect getRect() {
        return this.rect;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof CalendarField)) return false;
        final CalendarField that = (CalendarField) o;
        return this.id == that.id && this.localDateTime == that.localDateTime
                && this.rect.bottom == that.rect.bottom
                && this.rect.top == that.rect.top
                && this.rect.left == that.rect.left
                && this.rect.right == that.rect.right;
    }

    @Override
    public int hashCode() {

        return Objects.hash(this.id, this.rect.bottom, this.rect.left, this.rect.right, this.rect
                .top);
    }

    @Override
    public String toString() {
        return "CalendarField{" +
                "id=" + this.id +
                ", rect=" + this.rect.top + "," + this.rect.left + "," + this.rect.right + "," +
                this.rect.bottom;
    }
}
