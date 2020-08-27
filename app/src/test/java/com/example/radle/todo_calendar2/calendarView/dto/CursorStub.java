package com.example.radle.todo_calendar2.calendarView.dto;

import android.database.CursorWrapper;

import java.util.List;

class CursorStub extends CursorWrapper {
    private final List<List<String>> data;
    private int currentPosition = -1;

    CursorStub(final List<List<String>> data) {
        super(null);
        this.data = data;
    }

    @Override
    public void close() {

    }

    @Override
    public String getString(final int columnIndex) {
        return this.data.get(this.currentPosition).get(columnIndex);
    }

    @Override
    public long getLong(final int columnIndex) {
        return Long.valueOf(getString(columnIndex));
    }

    @Override
    public int getInt(final int columnIndex) {
        return Integer.valueOf(getString(columnIndex));
    }

    @Override
    public boolean moveToNext() {
        this.currentPosition++;
        return this.data.size() > this.currentPosition;
    }
}