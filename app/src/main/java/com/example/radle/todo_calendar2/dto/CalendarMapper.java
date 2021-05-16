package com.example.radle.todo_calendar2.dto;

import android.database.Cursor;
import android.provider.CalendarContract;

import java.util.LinkedList;
import java.util.List;

public class CalendarMapper {
    public List<Calendar> convertAll(Cursor cursor) {
        final List<Calendar> calendars = new LinkedList<>();
        while (cursor.moveToNext()) {
            calendars.add(createCalendar(cursor));
        }
        return calendars;
    }

    private Calendar createCalendar(Cursor cursor) {
        final String id = String.valueOf(cursor.getLong(CALENDAR_ID_INDEX));
        final String title = cursor.getString(CALENDAR_DISPLAY_NAME_INDEX);
        return new Calendar(id, title);
    }

    public static final String[] CALENDAR_PROJECTION = new String[]{
            CalendarContract.Calendars._ID,
            CalendarContract.Calendars.CALENDAR_TIME_ZONE,
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME
    };
    public static final int CALENDAR_ID_INDEX = 0;
    public static final int CALENDAR_ZONE_INDEX = 1;
    public static final int CALENDAR_DISPLAY_NAME_INDEX = 2;


}
