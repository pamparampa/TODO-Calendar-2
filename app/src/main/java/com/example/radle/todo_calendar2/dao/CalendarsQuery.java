package com.example.radle.todo_calendar2.dao;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CalendarContract;

import androidx.core.app.ActivityCompat;

import com.example.radle.todo_calendar2.calendarView.dto.CalendarEventMapper;
import com.example.radle.todo_calendar2.calendarView.dto.CalendarTimeZones;

import java.time.ZoneId;
import java.util.List;

class CalendarsQuery {

    private final Context context;

    CalendarsQuery(final Context context) {
        this.context = context;
    }

    ContentResolverProxy getContentResolver(final Context context) {
        return new ContentResolverProxy(context.getContentResolver());
    }

    CalendarTimeZones getCalendarsTimeZones(final List<String> calendarIds) {
        if (calendarIds.isEmpty()) {
            return new CalendarTimeZones();
        }
        checkPermission();
        final String selection = CalendarContract.Calendars._ID + " IN " +
                new QueryBracketsHelper().buildBracketsWithPlaceholders(calendarIds);
        try (final Cursor cursor = getContentResolver(this.context).query( //
                CalendarContract.Calendars.CONTENT_URI, CalendarEventMapper.CALENDAR_PROJECTION,
                selection, //
                calendarIds.toArray(new String[0]), //
                null)) {
            return convertToCalendarTimeZones(cursor);
        }
    }

    private CalendarTimeZones convertToCalendarTimeZones(final Cursor cursor) {
        final CalendarTimeZones calendarTimeZones = new CalendarTimeZones();
        if (cursor == null) return calendarTimeZones;
        while (cursor.moveToNext()) {
            if (cursor.getString(CalendarEventMapper.CALENDAR_ZONE_INDEX) != null) {
                calendarTimeZones.add(cursor.getString(CalendarEventMapper.CALENDAR_ID_INDEX),
                        ZoneId.of(cursor.getString(CalendarEventMapper.CALENDAR_ZONE_INDEX)));
            }
        }
        return calendarTimeZones;
    }

    void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this.context,
                Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            throw new SecurityException();
        }
    }
}
