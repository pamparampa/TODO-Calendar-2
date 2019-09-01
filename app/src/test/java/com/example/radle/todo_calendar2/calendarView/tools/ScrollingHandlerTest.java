package com.example.radle.todo_calendar2.calendarView.tools;

import com.example.radle.todo_calendar2.calendarView.dto.ScrollEffectParameters;
import com.example.radle.todo_calendar2.calendarView.dto.ScrollVelocity;

import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

public class ScrollingHandlerTest {

    private static final Duration LONG_SCROLL_DURATION = Duration.ofMinutes(2);
    private final ScrollingHandler subject = new ScrollingHandler(12);
    private final LocalDateTime dateTime = LocalDateTime.of(2019, Month.SEPTEMBER, 1, 0, 0);

    @Test
    public void handleScroll_shouldReturnScrollingBackToSamePositionData_whenPositionOfScrollNotChanged() {
        final ScrollEffectParameters expectedParameters = scrollBackToSamePositionParameters();
        Assert.assertEquals(expectedParameters, this.subject.handleScroll(new ScrollVelocity(0,
                LONG_SCROLL_DURATION), this.dateTime));
    }

    @Test
    public void handleScroll_shouldReturnScrollingRightData_whenScrollMovedRightByOneThirdOfScreen() {
        final ScrollEffectParameters expectedParameters = new ScrollEffectParameters(500,
                ScrollEffectParameters.Side.RIGHT,
                elementsToChangeAfterScrollRight());
        Assert.assertEquals(expectedParameters,
                this.subject.handleScroll(new ScrollVelocity(4, LONG_SCROLL_DURATION),
                        this.dateTime));
    }

    @Test
    public void handleScroll_shouldReturnScrollingBackToSamePositionData_whenScrollMovedLeftLessThanOneThirdOfScreen() {
        final ScrollEffectParameters expectedParameters = scrollBackToSamePositionParameters();
        Assert.assertEquals(expectedParameters, this.subject.handleScroll(new ScrollVelocity(3,
                LONG_SCROLL_DURATION), this.dateTime));
    }

    @Test
    public void handleScroll_shouldReturnScrollingRightData_whenScrolledRightFast() {
        final ScrollEffectParameters expectedParameters = new ScrollEffectParameters(400,
                ScrollEffectParameters.Side.RIGHT,
                elementsToChangeAfterScrollRight());
        Assert.assertEquals(expectedParameters, this.subject.handleScroll(new ScrollVelocity(3,
                Duration.ofMillis(600)), this.dateTime));
    }

    @Test
    public void handleScroll_shouldReturnScrollingLeftData_whenScrollMovedLeftByOneThirdOfScreen() {
        final ScrollEffectParameters expectedParameters = new ScrollEffectParameters(500,
                ScrollEffectParameters.Side.LEFT, elementsToChangeAfterScrollLeft());

        Assert.assertEquals(expectedParameters, this.subject.handleScroll(new ScrollVelocity(-4,
                LONG_SCROLL_DURATION), this.dateTime));
    }

    @Test
    public void handleScroll_shouldReturnScrollingLeftData_whenScrolledLeftFast() {
        final ScrollEffectParameters expectedParameters = new ScrollEffectParameters(400,
                ScrollEffectParameters.Side.LEFT,
                elementsToChangeAfterScrollLeft());
        Assert.assertEquals(expectedParameters, this.subject.handleScroll(new ScrollVelocity(-3,
                Duration.ofMillis(600)), this.dateTime));
    }

    private ScrollEffectParameters scrollBackToSamePositionParameters() {
        return new ScrollEffectParameters(500,
                ScrollEffectParameters.Side.SAME);
    }

    private ScrollEffectParameters.ElementsToChangeAfterScroll elementsToChangeAfterScrollRight() {
        return new ScrollEffectParameters.ElementsToChangeAfterScroll(LocalDate.of(2019,
                Month.SEPTEMBER, 2).atStartOfDay(), 0);
    }

    private ScrollEffectParameters.ElementsToChangeAfterScroll elementsToChangeAfterScrollLeft() {
        return new ScrollEffectParameters.ElementsToChangeAfterScroll(LocalDate.of(2019,
                Month.AUGUST, 19).atStartOfDay(), 3);
    }
}