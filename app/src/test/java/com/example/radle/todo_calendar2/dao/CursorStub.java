package com.example.radle.todo_calendar2.dao;

import android.database.sqlite.SQLiteCursor;


class CursorStub extends SQLiteCursor {

    CursorStub() {
        super(null, null, null);
    }

    @Override
    public void close() {

    }
}