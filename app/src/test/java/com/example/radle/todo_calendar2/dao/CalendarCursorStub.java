package com.example.radle.todo_calendar2.dao;

import com.example.radle.todo_calendar2.dto.CalendarMapper;

class CalendarCursorStub extends CursorStub<DbCalendar> {
    CalendarCursorStub(final Iterable<DbCalendar> data) {
        super(data);
    }

    @Override
    public String getString(final int columnIndex) {
        switch (columnIndex) {
            case CalendarMapper.CALENDAR_ID_INDEX:
                return this.current._id();
            case CalendarMapper.CALENDAR_ZONE_INDEX:
                return this.current.calendarZone();
            default:
                throw new IllegalArgumentException(wrongTypeMessage(columnIndex, "String"));
        }
    }
}
