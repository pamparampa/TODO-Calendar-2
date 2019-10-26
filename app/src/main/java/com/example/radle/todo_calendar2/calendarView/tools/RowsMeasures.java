package com.example.radle.todo_calendar2.calendarView.tools;

public class RowsMeasures {
    private static final int LABEL_COLUMN = 1;

    public int measureRowHeight(final int smallerDimension, final int numberOfClumns) {
        return smallerDimension / (numberOfClumns + LABEL_COLUMN);
    }
}
