package com.example.radle.todo_calendar2.dao;

import android.database.CursorWrapper;

import java.util.Collections;
import java.util.Iterator;


class CursorStub<T> extends CursorWrapper {
    private final Iterator<T> data;
    T current;

    CursorStub() {
        this(Collections.emptyList());
    }

    CursorStub(final Iterable<T> data) {
        super(null);
        this.data = data.iterator();
    }

    @Override
    public void close() {

    }

    @Override
    public boolean moveToNext() {
        if (this.data.hasNext()) {
            this.current = this.data.next();
            return true;
        }
        return false;
    }

    String wrongTypeMessage(final int columnIndex, final String type) {
        return String
                .format("column with id %s is not a %s or it does not exist", columnIndex, type);
    }
}