package com.example.radle.todo_calendar2.calendarView.tools;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;

public class ScrollVelocity {
    private static InitialPoint initialPoint;
    private Duration duration;
    private int distance;

    private ScrollVelocity() {
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
                new Builder()
                        .withDistance(x - initialPoint.x)
                        .withDuration(Duration.between(initialPoint.time, time))
                        .build();
        invalidate();
        return scrollVelocity;
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

    public static class Builder {

        private final ScrollVelocity scrollVelocity = new ScrollVelocity();

        public Builder withDuration(final Duration duration) {
            this.scrollVelocity.duration = duration;
            return this;
        }

        public Builder withDistance(final int distance) {
            this.scrollVelocity.distance = distance;
            return this;
        }

        public ScrollVelocity build() {
            return this.scrollVelocity;
        }
    }

    private static class InitialPoint {
        private int x;
        private LocalTime time;
    }
}
