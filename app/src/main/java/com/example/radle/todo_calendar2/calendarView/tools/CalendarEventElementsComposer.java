package com.example.radle.todo_calendar2.calendarView.tools;

import com.example.radle.todo_calendar2.calendarView.BoardScrollView;

public class CalendarEventElementsComposer {

    private static final float TEXT_HEIGHT_PROPORTIONS = 0.2f;

    public float getTextSize(final BoardScrollView.BoardParams boardParams) {
        return boardParams.getRowHeight() * TEXT_HEIGHT_PROPORTIONS;
    }
}
