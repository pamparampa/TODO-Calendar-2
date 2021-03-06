package com.example.radle.todo_calendar2.calendarView.scrolling;

import java.time.LocalDateTime;

import static java.lang.Math.abs;

public class ScrollingHandler {

    private final int screenWidth;
    private final AnimationDurationCounter animationDurationCounter =
            new AnimationDurationCounter();

    public ScrollingHandler(final int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public ScrollEffectParameters handleScroll(final ScrollVelocity scrollVelocity,
                                               final LocalDateTime currentlyVisibleDateTime) {
        final int durationOfAnimation = this.animationDurationCounter.count(scrollVelocity,
                this.screenWidth);
        final ScrollEffectParametersFactory scrollEffectParametersFactory =
                new ScrollEffectParametersFactory(durationOfAnimation, currentlyVisibleDateTime);
        if (scrollMoveWasTooSmallAndTooSlow(scrollVelocity, durationOfAnimation)) {
            return scrollEffectParametersFactory.scrollBackToSamePositionParameters();
        } else return scrollEffectParametersFactory.scrollParameters(scrollVelocity.getDistance());
    }

    private boolean scrollMoveWasTooSmallAndTooSlow(final ScrollVelocity scrollVelocity,
                                                    final int durationOfAnimation) {
        return scrollMoveWasToSmall(scrollVelocity)
                && !this.animationDurationCounter.fastScroll(durationOfAnimation);
    }

    private boolean scrollMoveWasToSmall(final ScrollVelocity scrollVelocity) {
        return abs(scrollVelocity.getDistance()) < this.screenWidth / 2;
    }
}
