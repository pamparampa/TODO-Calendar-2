package com.example.radle.todo_calendar2.dto;

import android.graphics.Rect;

import com.example.radle.todo_calendar2.calendarView.CalendarEventPartWithBounds;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class CalendarEventPartWithWidth extends CalendarEventPart {

    private float left;
    private float right;
    private final int divider;

    public CalendarEventPartWithWidth(final CalendarEvent calendarEvent, final String title,
                                      final LocalDateTime startTime, final LocalDateTime endTime,
                                      final float left, final float right, final int divider) {
        super(title, startTime, endTime, calendarEvent);
        this.left = left;
        this.right = right;
        this.divider = divider;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof CalendarEventPartWithWidth)) return false;
        final CalendarEventPartWithWidth that = (CalendarEventPartWithWidth) o;
        return super.equals(o) &&
                Float.compare(that.left, this.left) == 0 &&
                Float.compare(that.right, this.right) == 0 &&
                that.divider == this.divider;
    }

    @Override
    public String toString() {
        return "CalendarEventPartWithWidth{" +
                "event=" + getTitle() +
                ", start=" + getStartTime()
                .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)) +
                ", end=" + getEndTime()
                .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)) +
                ", left=" + this.left +
                ", right=" + this.right +
                ", divider=" + this.divider +
                '}';
    }

    public float averageSize(final float widthOfOtherEvent) {
        final float delta = ((widthOfOtherEvent - getWidth()) / 2);
        this.right += delta;
        return delta;
    }

    public void trimLeft(final float delta) {
        this.left += delta;
    }

    public float getWidth() {
        return this.right - this.left;
    }

    public CalendarEventPart withoutWidth() {
        return new CalendarEventPart(getTitle(), getStartTime(), getEndTime(), getCalendarEvent());
    }

    public CalendarEventPartWithWidth copy() {
        return new CalendarEventPartWithWidth(getCalendarEvent(), getTitle(), getStartTime(),
                getEndTime(), this.left, this.right, this.divider);
    }

    public float getLeft() {
        return this.left;
    }

    public float getRight() {
        return this.right;
    }

    public int getDivider() {
        return this.divider;
    }

    public CalendarEventPartWithWidth withNewTime(final LocalTime startTime,
                                                  final LocalTime endTime) {
        return new CalendarEventPartWithWidth(getCalendarEvent(), getTitle(),
                this.startTime.toLocalDate().atTime(startTime),
                this.endTime.toLocalDate().atTime(endTime),
                this.left, this.right, this.divider);
    }

    public CalendarEventPartWithBounds withBounds(Rect rect) {
        return new CalendarEventPartWithBounds(getTitle(), this.startTime,
                this.endTime,
                getCalendarEvent(),
                rect);
    }
}
