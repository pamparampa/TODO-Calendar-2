package com.example.radle.todo_calendar2.dao;

class DbCalendar {

    private final String _id;
    private final String calendarZone;

    DbCalendar(final String id, final String calendarZone) {
        this._id = id;
        this.calendarZone = calendarZone;
    }

    String _id() {
        return this._id;
    }

    String calendarZone() {
        return this.calendarZone;
    }
}
