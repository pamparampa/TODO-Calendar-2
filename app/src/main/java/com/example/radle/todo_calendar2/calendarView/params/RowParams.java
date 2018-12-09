package com.example.radle.todo_calendar2.calendarView.params;

import java.time.LocalDateTime;

public class RowParams {
    private final int width;
    private final int height;
    private final int numberOfColumns;
    private final int id;
    private final LocalDateTime firstDateTime;

    public RowParams(final int width, final int height, final int numberOfColumns, final int id,
                     final LocalDateTime firstDateTime) {
        this.width = width;
        this.height = height;
        this.numberOfColumns = numberOfColumns;
        this.id = id;
        this.firstDateTime = firstDateTime;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getNumberOfColumns() {
        return this.numberOfColumns;
    }

    public int getId() {
        return this.id;
    }

    public LocalDateTime getFirstDateTime() {
        return this.firstDateTime;
    }
}
