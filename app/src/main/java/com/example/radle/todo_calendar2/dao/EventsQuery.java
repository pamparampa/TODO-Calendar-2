package com.example.radle.todo_calendar2.dao;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;

import androidx.core.app.ActivityCompat;

import com.example.radle.todo_calendar2.dto.CalendarEventMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class EventsQuery {
    private static final int DATE_PARAMETERS_SIZE = 2;
    private final Uri uri = CalendarContract.Events.CONTENT_URI;

    private final Context context;

    EventsQuery(final Context context) {
        this.context = context;
    }

    ContentResolverProxy getContentResolver(final Context context) {
        return new ContentResolverProxy(context.getContentResolver());
    }

    Optional<Cursor> query(final List<String> calendars, final LocalDateTime startDate,
                           final LocalDateTime endDate) {
        checkPermission();
        if (calendars.isEmpty()) {
            return Optional.empty();
        }
        final String selection = buildSelection(calendars);
        final String[] args = buildArgs(calendars, endDate, startDate);

        final Cursor cursor = getContentResolver(this.context)
                .query(this.uri, CalendarEventMapper.EVENT_PROJECTION, selection, args, null);
        return Optional.ofNullable(cursor);
    }

    void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this.context,
                Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            throw new SecurityException();  // TODO to chyba trzeba jakos ladniej obsluzyc
        }
    }

    private String buildSelection(final List<String> calendars) {
        return CalendarContract.Events.CALENDAR_ID + " IN " + getCalendarPlaceholders(calendars)
                + " AND " + CalendarContract.Events.DTSTART + " < ?"
                + " AND " + CalendarContract.Events.DTEND + " > ?";
    }

    private String getCalendarPlaceholders(final List<String> calendars) {
        return new QueryBracketsHelper().buildBracketsWithPlaceholders(calendars);
    }

    private String[] buildArgs(final List<String> calendars, final LocalDateTime startDate,
                               final LocalDateTime endDate) {
        final int numberOfArgs = calendars.size() + DATE_PARAMETERS_SIZE;
        final List<String> args = new ArrayList<>(numberOfArgs);
        args.addAll(calendars);
        args.add(String.valueOf(Utils.getDatabaseFormatOfDate(startDate)));
        args.add(String.valueOf(Utils.getDatabaseFormatOfDate(endDate)));
        return args.toArray(new String[0]);
    }
}
