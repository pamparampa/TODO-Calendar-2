package com.example.radle.todo_calendar2.calendarView.tools.events.conflicts;

import com.example.radle.todo_calendar2.calendarView.dto.CalendarEventPartWithWidth;

import java.util.List;
import java.util.Objects;

class ChainedCalendarEventPart {
    private final List<ChainedCalendarEventPart> conflictedCalendarEventsOnTheRight;
    private final CalendarEventPartWithWidth eventPart;

    public ChainedCalendarEventPart(final CalendarEventPartWithWidth eventPart,
                                    final List<ChainedCalendarEventPart> conflictedCalendarEventsOnTheRight) {
        this.eventPart = eventPart.copy();
        this.conflictedCalendarEventsOnTheRight = conflictedCalendarEventsOnTheRight;
    }

    public List<ChainedCalendarEventPart> getConflictedCalendarEventsOnTheRight() {
        return this.conflictedCalendarEventsOnTheRight;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof ChainedCalendarEventPart)) return false;
        final ChainedCalendarEventPart that = (ChainedCalendarEventPart) o;
        return this.eventPart.equals(that.eventPart) &&
                this.conflictedCalendarEventsOnTheRight
                        .equals(that.conflictedCalendarEventsOnTheRight);
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(this.eventPart, this.conflictedCalendarEventsOnTheRight);
    }

    @Override
    public String toString() {
        return "ChainedCalendarEventPart{" +
                "calendarEventPart=" + this.eventPart.getTitle() +
                ", conflictedCalendarEventsOnTheRight=" + this.conflictedCalendarEventsOnTheRight +
                '}';
    }

    public CalendarEventPartWithWidth unchain() {
        return this.eventPart;
    }

    public float getWidth() {
        return this.eventPart.getWidth();
    }

    public float averageSize(final float minimumWidthOfConflictedEvents) {
        return this.eventPart.averageSize(minimumWidthOfConflictedEvents);
    }

    public void trimLeft(final float delta) {
        this.eventPart.trimLeft(delta);
    }
}
