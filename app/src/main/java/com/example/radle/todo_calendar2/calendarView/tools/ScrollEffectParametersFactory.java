package com.example.radle.todo_calendar2.calendarView.tools;

import com.example.radle.todo_calendar2.calendarView.dto.ScrollEffectParameters;

import java.time.LocalDateTime;

public class ScrollEffectParametersFactory {
    private final static int LAST_ELEMENT_ID = 3;
    private static final int FIRST_ELEMENT_ID = 0;
    private final int durationOfAnimation;
    private final LocalDateTime dateTime;

    public ScrollEffectParametersFactory(final int durationOfAnimation,
                                         final LocalDateTime dateTime) {
        this.durationOfAnimation = durationOfAnimation;
        this.dateTime = dateTime;
    }


    public ScrollEffectParameters scrollBackToSamePositionParameters() {
        return new ScrollEffectParameters(this.durationOfAnimation,
                ScrollEffectParameters.Side.SAME);
    }

    public ScrollEffectParameters scrollParameters(final int distance) {
        if (distance > 0) return scrollRightParameters();
        else return scrollLeftParameters();
    }

    private ScrollEffectParameters scrollRightParameters() {
        final LocalDateTime newWeeDateTime =
                new WeekBeginningDateTimeProvider().getNextWeekBeginning(this.dateTime);
        return getScrollEffectParameters(newWeeDateTime, ScrollEffectParameters.Side.RIGHT,
                FIRST_ELEMENT_ID);
    }

    private ScrollEffectParameters scrollLeftParameters() {
        final LocalDateTime newWeekDateTime =
                new WeekBeginningDateTimeProvider().getPrevWeekBeginning(this.dateTime);
        return getScrollEffectParameters(newWeekDateTime, ScrollEffectParameters.Side.LEFT,
                LAST_ELEMENT_ID);
    }

    private ScrollEffectParameters getScrollEffectParameters(
            final LocalDateTime newWeeDateTime, final ScrollEffectParameters.Side right,
            final int firstElementId) {
        return new ScrollEffectParameters(this.durationOfAnimation, right,
                new ScrollEffectParameters.ElementsToChangeAfterScroll(
                        newWeeDateTime, firstElementId
                ));
    }
}
