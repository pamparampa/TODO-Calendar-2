package com.example.radle.todo_calendar2.calendarView.tools.events.conflicts;

import com.example.radle.todo_calendar2.calendarView.dto.CalendarEventPart;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

class ChainedCalendarEventPart {
    private final CalendarEventPart calendarEventPart;
    private final List<ChainedCalendarEventPart> conflictedCalendarEventsOnTheRight;
    private ResizeParameters resizeParameters;

    public ChainedCalendarEventPart(final CalendarEventPart calendarEventPart,
                                    final List<ChainedCalendarEventPart> conflictedCalendarEventsOnTheRight,
                                    final ResizeParameters resizeParameters) {
        this(calendarEventPart, conflictedCalendarEventsOnTheRight);
        this.resizeParameters = resizeParameters;
    }

    public ChainedCalendarEventPart(final CalendarEventPart calendarEventPart,
                                    final List<ChainedCalendarEventPart> conflictedCalendarEventsOnTheRight) {
        this.calendarEventPart = calendarEventPart;
        this.conflictedCalendarEventsOnTheRight = conflictedCalendarEventsOnTheRight;
    }

    public CalendarEventPart getCalendarEventPart() {
        return this.calendarEventPart;
    }

    public List<ChainedCalendarEventPart> getConflictedCalendarEventsOnTheRight() {
        return this.conflictedCalendarEventsOnTheRight;
    }

    public Optional<ResizeParameters> getResizeParameters() {
        return Optional.empty();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof ChainedCalendarEventPart)) return false;
        final ChainedCalendarEventPart that = (ChainedCalendarEventPart) o;
        return this.calendarEventPart == that.calendarEventPart &&
                this.conflictedCalendarEventsOnTheRight
                        .equals(that.conflictedCalendarEventsOnTheRight) &&
                Objects.equals(this.resizeParameters, that.resizeParameters);
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(this.calendarEventPart, this.conflictedCalendarEventsOnTheRight,
                        this.resizeParameters);
    }

    @Override
    public String toString() {
        return "ChainedCalendarEventPart{" +
                "calendarEventPart=" + this.calendarEventPart.getTitle() +
                ", conflictedCalendarEventsOnTheRight=" + this.conflictedCalendarEventsOnTheRight +
                ", resizeParameters=" + this.resizeParameters +
                '}';
    }

    static class ResizeParameters {
        private final int left;
        private final int right;

        ResizeParameters(final int left, final int right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (!(o instanceof ResizeParameters)) return false;
            final ResizeParameters that = (ResizeParameters) o;
            return Float.compare(that.left, this.left) == 0 &&
                    Float.compare(that.right, this.right) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.left, this.right);
        }

        @Override
        public String toString() {
            return "ResizeParameters{" +
                    "left=" + this.left +
                    ", right=" + this.right +
                    '}';
        }
    }
}
