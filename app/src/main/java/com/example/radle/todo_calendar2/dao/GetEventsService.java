package com.example.radle.todo_calendar2.dao;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.ResultReceiver;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.example.radle.todo_calendar2.calendarView.dto.CalendarEvent;
import com.example.radle.todo_calendar2.calendarView.dto.CalendarEventMapper;
import com.example.radle.todo_calendar2.calendarView.dto.CalendarTimeZones;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

//import android.provider.CalendarContract;

public class GetEventsService extends JobIntentService {

    CalendarEventMapper calendarEventMapper = new CalendarEventMapper();

   /* public static final String[] CALENDAR_PROJECTION = new String[]{
            CalendarContract.Calendars._ID,                           // 0
            CalendarContract.Calendars.ACCOUNT_NAME,                  // 1
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,         // 2
            CalendarContract.Calendars.CALENDAR_TIME_ZONE                  // 3
    };*/

    @Override
    protected void onHandleWork(@NonNull final Intent intent) {
        //writeAllCalendars();
        final Context context = getApplicationContext();
        final EventsQuery eventsQuery = new EventsQuery(context);
        final List<String> calendarIds = getCalendarIds(intent);
        final Optional<Cursor> cursor = queryForEvents(eventsQuery, intent, calendarIds);
        if (cursor.isPresent()) {
            final CalendarTimeZones calendarsTimeZones = getCalendarTimeZones(context, calendarIds);
            final List<CalendarEvent> calendarEvents =
                    this.calendarEventMapper.convertAll(cursor.get(), calendarsTimeZones);
            sendResult(intent, calendarEvents);
        }
    }

    private List<String> getCalendarIds(@NonNull final Intent intent) {
        return Arrays.asList(intent.getStringArrayExtra(CalendarEventsDao.CALENDAR_IDS));
    }

    private void sendResult(@NonNull final Intent intent,
                            final List<CalendarEvent> calendarEvents) {
        final ResultReceiver resultReceiver = intent.getParcelableExtra(CalendarEventsDao.RECEIVER);
        resultReceiver.send(CalendarEventsDao.GET_EVENTS, prepareBundle(calendarEvents));
    }

    private CalendarTimeZones getCalendarTimeZones(final Context context,
                                                   final List<String> calendarIds) {
        // TODO mozna tu zrobic jakiegos cache'a
        final CalendarsQuery calendarsQuery = new CalendarsQuery(context);
        return calendarsQuery.getCalendarsTimeZones(calendarIds);
    }

    private Optional<Cursor> queryForEvents(final EventsQuery eventsQuery, final Intent intent,
                                            final List<String> calendarIds) {
        final LocalDateTime firstDateTime = getFirstDateTime(intent);
        final LocalDateTime lastDateTime = getLastDateTime(intent, firstDateTime);
        return eventsQuery
                .query(calendarIds, firstDateTime, lastDateTime);
    }

    private LocalDateTime getFirstDateTime(@NonNull final Intent intent) {
        return LocalDateTime.parse(intent.getStringExtra(CalendarEventsDao.FIRST_DATE_TIME));
    }

    private LocalDateTime getLastDateTime(final Intent intent, final LocalDateTime firstDateTime) {
        if (intent.hasExtra(CalendarEventsDao.LAST_DATE_TIME)) {
            return LocalDateTime.parse(intent.getStringExtra(CalendarEventsDao.LAST_DATE_TIME));
        } else {
            return firstDateTime.plusWeeks(1);
        }
    }

    private Bundle prepareBundle(final List<CalendarEvent> calendarEvents) {
        final Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(CalendarEventsDao.EVENTS, new ArrayList<>(calendarEvents));
        return bundle;
    }

    /*private void writeAllCalendars() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            throw new SecurityException();
        }
        final Cursor cursor = this.getApplicationContext().getContentResolver()
                .query(this.uri, CALENDAR_PROJECTION, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                System.out.println(cursor.getLong(PROJECTION_ID_INDEX)
                        + " " + cursor.getString(PROJECTION_DISPLAY_NAME_INDEX)
                        + " " + cursor.getString(PROJECTION_ACCOUNT_NAME_INDEX)
                        + " " + cursor.getString(PROJECTION_OWNER_ACCOUNT_INDEX));
            }
            cursor.close();
        }
    }*/
}
