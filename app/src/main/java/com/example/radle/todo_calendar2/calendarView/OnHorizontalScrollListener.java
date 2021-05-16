package com.example.radle.todo_calendar2.calendarView;

interface OnHorizontalScrollListener {
    void startScrolling(final float x);

    void finishScrolling(final float x, final float y);
}
