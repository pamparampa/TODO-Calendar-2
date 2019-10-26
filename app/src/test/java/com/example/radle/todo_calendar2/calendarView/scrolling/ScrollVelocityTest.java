package com.example.radle.todo_calendar2.calendarView.scrolling;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalTime;

public class ScrollVelocityTest {

    @Before
    public void invalidateScrollVelocity() {
        ScrollVelocity.invalidate();
    }

    @Test(expected = IllegalStateException.class)
    public void finishMeasurement_shouldThrowException_whenMeasurementNotStarted() {
        ScrollVelocity.finishMeasurement(0, LocalTime.now());
    }

    @Test
    public void startMeasurement_shouldReturnZeroVelocity_whenPositionOfScrollDidNotChanged() {
        final ScrollVelocity expectedVelocity =
                new ScrollVelocity(0, Duration.ofMinutes(2));
        ScrollVelocity.startMeasurement(0, LocalTime.of(0, 0));
        Assert.assertEquals(expectedVelocity, ScrollVelocity.finishMeasurement(0, LocalTime.of(0,
                2)));

    }

    @Test
    public void startMeasurement_shouldReturnValidVelocity_whenPositionOfScrollChanged() {
        final ScrollVelocity expectedVelocity =
                new ScrollVelocity(1, Duration.ofMinutes(2));
        ScrollVelocity.startMeasurement(3, LocalTime.of(0, 0));
        Assert.assertEquals(expectedVelocity, ScrollVelocity.finishMeasurement(4, LocalTime.of(0,
                2)));
    }

    @Test(expected = IllegalStateException.class)
    public void startMeasurement_shouldThrowException_whenMeasurementIsAlreadyStarted() {
        ScrollVelocity.startMeasurement(0, LocalTime.of(0, 0));
        ScrollVelocity.startMeasurement(0, LocalTime.of(0, 0));
    }

    @Test(expected = IllegalStateException.class)
    public void finishMeasurement_shoulThrowException_whenMeasurementIsAlreadyFinished() {
        ScrollVelocity.startMeasurement(3, LocalTime.of(0, 0));
        ScrollVelocity.finishMeasurement(0, LocalTime.of(0, 2));
        ScrollVelocity.finishMeasurement(0, LocalTime.of(0, 2));
    }

    @Test
    public void finishMeasurement_shouldReturnValidValocity_whenItWasMeasuredBetweenDays() {
        final ScrollVelocity expectedVelocity =
                new ScrollVelocity(1, Duration.ofMinutes(2));
        ScrollVelocity.startMeasurement(3, LocalTime.of(23, 59));
        Assert.assertEquals(expectedVelocity, ScrollVelocity.finishMeasurement(4, LocalTime.of(0,
                1)));
    }
}