package com.example.radle.todo_calendar2.calendarView.tools;

import com.example.radle.todo_calendar2.calendarView.dto.ScrollEffectParameters;
import com.example.radle.todo_calendar2.calendarView.dto.ScrollVelocity;

import java.time.LocalDateTime;

import static java.lang.Math.abs;

public class ScrollingHandler {

    private final int screenWidth;
    private final AnimationDurationCounter animationDurationCounter =
            new AnimationDurationCounter();
    long s = 3L;

    public ScrollingHandler(final int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public ScrollEffectParameters handleScroll(final ScrollVelocity scrollVelocity,
                                               final LocalDateTime dateTime) {
        final int durationOfAnimation = this.animationDurationCounter.count(scrollVelocity,
                this.screenWidth);
        final ScrollEffectParametersFactory scrollEffectParametersFactory =
                new ScrollEffectParametersFactory(durationOfAnimation, dateTime);
        if (scrollMoveWasTooSmallandTooSlow(scrollVelocity, durationOfAnimation)) {
            return scrollEffectParametersFactory.scrollBackToSamePositionParameters();
        } else return scrollEffectParametersFactory.scrollParameters(scrollVelocity.getDistance());
    }

    private boolean scrollMoveWasTooSmallandTooSlow(final ScrollVelocity scrollVelocity,
                                                    final int durationOfAnimation) {
        return scrollMoveWasToSmall(scrollVelocity)
                && !this.animationDurationCounter.fastScroll(durationOfAnimation);
    }

    private boolean scrollMoveWasToSmall(final ScrollVelocity scrollVelocity) {
        return abs(scrollVelocity.getDistance()) < this.screenWidth / 3;
    }
}
