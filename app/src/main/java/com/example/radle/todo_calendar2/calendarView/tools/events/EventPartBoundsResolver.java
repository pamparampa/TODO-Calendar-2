package com.example.radle.todo_calendar2.calendarView.tools.events;

import android.graphics.Rect;

import com.example.radle.todo_calendar2.calendarView.BoardScrollView;
import com.example.radle.todo_calendar2.calendarView.dto.CalendarEventPartWithWidth;

import java.time.LocalTime;
import java.time.Period;

public class EventPartBoundsResolver {

    private static final int ADDITIONAL_LABEL_COLUMN = 1;
    private static final int NUMBER_OF_COLUMNS = 24;
    private static final int NUMBER_OF_SECONDS_IN_DAY = 86400;
    private final BoardScrollView.BoardParams boardParams;

    public EventPartBoundsResolver(final BoardScrollView.BoardParams boardParams) {
        this.boardParams = boardParams;
    }

    public Rect resolveBounds(final CalendarEventPartWithWidth event) {
        final Rect rect = new Rect();
        rect.left = getColumnPlace(getShift(event, event.getLeft()));
        rect.top = getVerticalPlace(event.getStartTime().toLocalTime());
        rect.right = getColumnPlace(getShift(event, event.getRight()));
        rect.bottom = getBottom(event);
        return rect;
    }

    private float getShift(final CalendarEventPartWithWidth event, final float shiftInsideColumn) {
        return getNumberOfDay(event) + ADDITIONAL_LABEL_COLUMN + (shiftInsideColumn / event
                .getDivider());
    }

    private int getBottom(final CalendarEventPartWithWidth event) {
        final int numberOfDaysOfThisEvent = Period.between(event.getStartTime().toLocalDate(),
                event.getEndTime().toLocalDate()).getDays();
        return getVerticalPlace(event.getEndTime().toLocalTime()) +
                numberOfDaysOfThisEvent * getHeightOfWholeDay();
    }

    private int getVerticalPlace(final LocalTime time) {
        return (time.toSecondOfDay() * getHeightOfWholeDay()) / NUMBER_OF_SECONDS_IN_DAY;
    }

    private int getHeightOfWholeDay() {
        return NUMBER_OF_COLUMNS * this.boardParams.getRowHeight();
    }

    private int getColumnPlace(final float shift) {
        return (int) (shift * this.boardParams.getWidth()) /
                (this.boardParams.getNumberOfColumns() + ADDITIONAL_LABEL_COLUMN);
    }

    private int getNumberOfDay(final CalendarEventPartWithWidth event) {
        return Period.between(
                this.boardParams.getFirstDateTime().toLocalDate(),
                event.getStartTime().toLocalDate()).getDays();
    }
}
