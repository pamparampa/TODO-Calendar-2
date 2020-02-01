package com.example.radle.todo_calendar2.calendarView.tools.events.conflicts;

import com.example.radle.todo_calendar2.calendarView.dto.CalendarEventPart;
import com.example.radle.todo_calendar2.calendarView.dto.CalendarEventPartWithWidth;

import java.util.List;
import java.util.Objects;

class ChainedCalendarEventPart extends CalendarEventPartWithWidth {
    private final List<ChainedCalendarEventPart> conflictedCalendarEventsOnTheRight;

    public ChainedCalendarEventPart(final CalendarEventPart calendarEventPart, final float from,
                                    final float to,
                                    final List<ChainedCalendarEventPart> conflictedCalendarEventsOnTheRight) {
        super(calendarEventPart, from, to);
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
        return this.eventPart == that.eventPart &&
                this.conflictedCalendarEventsOnTheRight
                        .equals(that.conflictedCalendarEventsOnTheRight) &&
                this.from == that.from && this.to == that.to;
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(this.eventPart, this.conflictedCalendarEventsOnTheRight,
                        this.from, this.to);
    }

    @Override
    public String toString() {
        return "ChainedCalendarEventPart{" +
                "calendarEventPart=" + this.eventPart.getTitle() +
                ", from=" + this.from +
                ", to=" + this.to +
                ", conflictedCalendarEventsOnTheRight=" + this.conflictedCalendarEventsOnTheRight +
                '}';
    }

    public CalendarEventPartWithWidth unchain() {
        return new CalendarEventPartWithWidth(this.eventPart, this.from, this.to);
    }
}
