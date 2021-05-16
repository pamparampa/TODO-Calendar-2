package com.example.radle.todo_calendar2.dao;

import com.example.radle.todo_calendar2.dto.CalendarEventMapper;


public class CalendarEventCursorStub extends CursorStub<DbCalendarEvent> {

    CalendarEventCursorStub(final Iterable<DbCalendarEvent> data) {
        super(data);
    }

    @Override
    public String getString(final int columnIndex) {
        switch (columnIndex) {
            case CalendarEventMapper.CALENDAR_ID_INDEX:
                return this.current.calendar_id();
            case CalendarEventMapper.TITLE_INDEX:
                return this.current.title();
            case CalendarEventMapper.EVENT_TIMEZONE_INDEX:
                return this.current.localZone();
            case CalendarEventMapper.EVENT_END_TIMEZONE_INDEX:
                return this.current.endTimeZone();
            default:
                throw new IllegalArgumentException(
                        wrongTypeMessage(columnIndex, "String"));
        }
    }

    @Override
    public long getLong(final int columnIndex) {
        switch (columnIndex) {
            case CalendarEventMapper.DTSTART_INDEX:
                return this.current.dtstart();
            case CalendarEventMapper.DTEND_INDEX:
                return this.current.dtend();
            default:
                throw new IllegalArgumentException(
                        wrongTypeMessage(columnIndex, "long"));
        }
    }

    @Override
    public int getInt(final int columnIndex) {
        switch (columnIndex) {
            case CalendarEventMapper.DISPLAY_COLOR_INDEX:
                return this.current.displayColor();
            default:
                throw new IllegalArgumentException(
                        wrongTypeMessage(columnIndex, "int"));
        }
    }

}
