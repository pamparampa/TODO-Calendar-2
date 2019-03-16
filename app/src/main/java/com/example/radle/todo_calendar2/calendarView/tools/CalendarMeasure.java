package com.example.radle.todo_calendar2.calendarView.tools;

import com.example.radle.todo_calendar2.calendarView.BoardListView;

public class CalendarMeasure {
    private static final int LABEL_COLUMN = 1;

    public int measureRowHeight(final BoardListView.BoardParams boardParams) {
        return boardParams.getWidth() / (boardParams.getNumberOfColumns() + LABEL_COLUMN);
    }
}
