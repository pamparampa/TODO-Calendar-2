package com.example.radle.todo_calendar2.calendarView.scrolling;

import static java.lang.Math.abs;
import static java.lang.Math.min;

class AnimationDurationCounter {
    final static int STANDARD_ANIMATION_DURATION = 500;

    int count(final ScrollVelocity scrollVelocity, final int screenWidth) {
        if (scrollVelocity.getDistance() == 0)
            return STANDARD_ANIMATION_DURATION;
        else {
            final double partOfScreenScrolled =
                    (double) abs(scrollVelocity.getDistance()) / screenWidth;
            return (int) min((scrollVelocity.getDuration().toMillis() / partOfScreenScrolled / 6)
                    , STANDARD_ANIMATION_DURATION);
        }
    }


    boolean fastScroll(final int durationOfAnimation) {
        return durationOfAnimation < STANDARD_ANIMATION_DURATION / 2;
    }
}