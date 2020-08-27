package com.example.radle.todo_calendar2.calendarView.dto;

import android.database.Cursor;
import android.provider.CalendarContract;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.LinkedList;
import java.util.List;

public class CalendarEventMapper {
    public static final String[] EVENT_PROJECTION = new String[]{
            CalendarContract.Events.CALENDAR_ID,
            CalendarContract.Events.TITLE,
            CalendarContract.Events.DTSTART,
            CalendarContract.Events.DTEND,
            CalendarContract.Events.EVENT_TIMEZONE,
            CalendarContract.Events.EVENT_END_TIMEZONE,
            CalendarContract.Events.DISPLAY_COLOR
    };
    public static final String[] CALENDAR_PROJECTION = new String[]{
            CalendarContract.Calendars._ID,
            CalendarContract.Calendars.CALENDAR_TIME_ZONE
    };
    public static final int CALENDAR_ID_INDEX = 0;
    public static final int CALENDAR_ZONE_INDEX = 1;
    private static final int TITLE_INDEX = 1;
    private static final int DTSTART_INDEX = 2;
    private static final int DTENT_INDEX = 3;
    private static final int EVENT_TIMEZONE_INDEX = 4;
    private static final int EVENT_END_TIMEZONE_INDEX = 5;
    private static final int DISPLAY_COLOR_INDEX = 6;
    private static final int MILIS_IN_SECOND = 1000;
    private static final int NANOS_IN_MILI = 1000000;

    public List<CalendarEvent> convertAll(final Cursor cursor,
                                          final CalendarTimeZones calendarTimeZones) {
        final List<CalendarEvent> calendarEvents = new LinkedList<>();
        while (cursor.moveToNext()) {
            calendarEvents.add(createCalendarEvent(cursor, calendarTimeZones));
        }
        return calendarEvents;
    }

    private CalendarEvent createCalendarEvent(final Cursor cursor,
                                              final CalendarTimeZones calendarTimeZones) {
        final Long dtstart = cursor.getLong(DTSTART_INDEX);
        final Long dtend = cursor.getLong(DTENT_INDEX);
        return new CalendarEvent(
                cursor.getString(TITLE_INDEX),
                toLocalDate(dtstart, getStartDateZoneId(cursor, calendarTimeZones)),
                toLocalDate(dtend, getEndDateZoneId(cursor, calendarTimeZones)),
                cursor.getInt(DISPLAY_COLOR_INDEX));
    }

    private ZoneId getEndDateZoneId(final Cursor cursor,
                                    final CalendarTimeZones calendarTimeZones) {
        final String zoneId = cursor.getString(EVENT_END_TIMEZONE_INDEX);
        if (zoneId != null && !"0".equals(zoneId))
            return ZoneId.of(zoneId);
        else
            return getStartDateZoneId(cursor, calendarTimeZones);
    }

    private ZoneId getStartDateZoneId(final Cursor cursor,
                                      final CalendarTimeZones calendarTimeZones) {
        final String zoneId = cursor.getString(EVENT_TIMEZONE_INDEX);
        if (zoneId != null && !"0".equals(zoneId))
            return ZoneId.of(zoneId);
        else {
            final String calendarId = cursor.getString(CALENDAR_ID_INDEX);
            if (calendarTimeZones.hasDataForId(calendarId))
                return calendarTimeZones.get(calendarId);
            else
                return ZoneId.systemDefault();
        }
    }

    private LocalDateTime toLocalDate(final Long dtstart, final ZoneId zone) {
        return LocalDateTime.ofEpochSecond(
                Math.floorDiv(dtstart, MILIS_IN_SECOND),
                (int) Math.floorMod(dtstart, MILIS_IN_SECOND) * NANOS_IN_MILI,
                zone.getRules().getOffset(Instant.ofEpochMilli(dtstart)));
    }
}
