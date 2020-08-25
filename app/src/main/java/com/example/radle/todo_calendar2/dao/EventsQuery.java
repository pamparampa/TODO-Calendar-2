package com.example.radle.todo_calendar2.dao;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;

import androidx.core.app.ActivityCompat;

import com.example.radle.todo_calendar2.calendarView.dto.CalendarEventMapper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

class EventsQuery {
    private static final int MILIS_IN_SECOND = 1000;
    private static final int NANOS_IN_MILI = 1000000;
    private static final int DATE_PARAMETERS_SIZE = 2;
    private final ContentResolver contentResolver;
    private final Uri uri = CalendarContract.Events.CONTENT_URI;

    private final Context context;

    EventsQuery(final Context context) {
        this.context = context;
        this.contentResolver = context.getContentResolver();
    }

    Optional<Cursor> query(final List<String> calendars, final LocalDateTime startDate,
                           final LocalDateTime endDate) {
        checkPermission();
        if (calendars.isEmpty()) {
            return Optional.empty();
        }
        final String selection = buildSelection(calendars);
        final String[] args = buildArgs(calendars, startDate, endDate);

        final Cursor cursor = this.contentResolver
                .query(this.uri, CalendarEventMapper.EVENT_PROJECTION, selection, args, null);
        return cursor != null ? Optional.of(cursor) : Optional.empty();
    }

    void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this.context,
                Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            throw new SecurityException();
        }
    }

    private String buildSelection(final List<String> calendars) {
        return CalendarContract.Events.CALENDAR_ID + " IN " + getCalendarPlaceholders(calendars)
                + " AND " + CalendarContract.Events.DTSTART + " >= ?"
                + " AND " + CalendarContract.Events.DTEND + " <= ?";
    }

    private String getCalendarPlaceholders(final List<String> calendars) {
        return new QueryBracketsHelper().buildBracketsWithPlaceholders(calendars);
    }

    private String[] buildArgs(final List<String> calendars, final LocalDateTime startDate,
                               final LocalDateTime endDate) {
        final int numberOfArgs = calendars.size() + DATE_PARAMETERS_SIZE;
        final List<String> args = new ArrayList<>(numberOfArgs);
        args.addAll(calendars);
        args.add(getDatabaseFormatOfDate(startDate));
        args.add(getDatabaseFormatOfDate(endDate));
        return args.toArray(new String[numberOfArgs]);
    }

    private String getDatabaseFormatOfDate(final LocalDateTime date) {
        final long epochSecond = date.toEpochSecond(
                ZoneId.of(TimeZone.getDefault().getID()).getRules().getOffset(date));
        return String.valueOf(
                (epochSecond * MILIS_IN_SECOND) + (date.getNano() / NANOS_IN_MILI));
    }
}
