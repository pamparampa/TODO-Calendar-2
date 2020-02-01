package com.example.radle.todo_calendar2.calendarView.dto;

import java.util.Objects;

public class CalendarEventPartWithWidth {

    protected final CalendarEventPart eventPart;
    protected float from;
    protected float to;

    public CalendarEventPartWithWidth(final CalendarEventPart eventPart, final float from,
                                      final float to) {
        this.eventPart = eventPart;
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof CalendarEventPartWithWidth)) return false;
        final CalendarEventPartWithWidth that = (CalendarEventPartWithWidth) o;
        return Float.compare(that.from, this.from) == 0 &&
                Float.compare(that.to, this.to) == 0 &&
                Objects.equals(this.eventPart, that.eventPart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.eventPart, this.from, this.to);
    }

    @Override
    public String toString() {
        return "CalendarEventPartWithWidth{" +
                "calendarEventPart=" + this.eventPart.getTitle() +
                ", from=" + this.from +
                ", to=" + this.to +
                '}';
    }

    public float averageSize(final float widthOfOtherEvent) {
        final float delta = ((widthOfOtherEvent - getWidth()) / 2);
        this.to += delta;
        return delta;
    }

    public void trimLeft(final float delta) {
        this.from += delta;
    }

    public float getWidth() {
        return this.to - this.from;
    }

    public CalendarEventPart getEventPart() {
        return this.eventPart;
    }
}
