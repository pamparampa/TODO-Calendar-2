package com.example.radle.todo_calendar2.calendarView.scrolling;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;

public class ScrollVelocity {
    private static InitialPoint initialPoint;
    private final Duration duration;
    private final int distance;

    public ScrollVelocity(final int distance, final Duration duration) {
        this.distance = distance;
        this.duration = duration;
    }

    public static void startMeasurement(final int x, final LocalTime time) {
        if (initialPoint != null) {
            throw new IllegalStateException("measurement cannot be started cause it has been " +
                    "already started");
        }
        initialPoint = new InitialPoint();
        initialPoint.x = x;
        initialPoint.time = time;
    }

    public static ScrollVelocity finishMeasurement(final int x, final LocalTime time) {
        if (initialPoint == null) {
            throw new IllegalStateException("measurement cannot be ended cause it has not been " +
                    "started yet");
        }
        final ScrollVelocity scrollVelocity =
                new ScrollVelocity(x - initialPoint.x, countDuration(time));
        invalidate();
        return scrollVelocity;
    }

    private static Duration countDuration(final LocalTime time) {
        final Duration duration = Duration.between(initialPoint.time, time);
        if (duration.isNegative()) {
            return Duration.ofDays(1).minus(duration.abs());
        } else return duration;
    }

    public static void invalidate() {
        initialPoint = null;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof ScrollVelocity)) return false;
        final ScrollVelocity that = (ScrollVelocity) o;
        return this.distance == that.distance &&
                Objects.equals(this.duration, that.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.duration, this.distance);
    }

    public int getDistance() {
        return this.distance;
    }

    public Duration getDuration() {
        return this.duration;
    }

    private static class InitialPoint {
        private int x;
        private LocalTime time;
    }
}
