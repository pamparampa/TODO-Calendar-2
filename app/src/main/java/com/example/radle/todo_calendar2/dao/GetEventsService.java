package com.example.radle.todo_calendar2.dao;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.ResultReceiver;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.example.radle.todo_calendar2.constant.RequestCodes;
import com.example.radle.todo_calendar2.dto.CalendarEvent;
import com.example.radle.todo_calendar2.dto.CalendarEventMapper;
import com.example.radle.todo_calendar2.dto.CalendarTimeZones;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

//import android.provider.CalendarContract;

public class GetEventsService extends JobIntentService {

    CalendarEventMapper calendarEventMapper = new CalendarEventMapper();

    public void enqueue(final Context context, final Intent intent) {
        enqueueWork(context, GetEventsService.class, 1, intent);
    }

    @Override
    protected void onHandleWork(@NonNull final Intent intent) { // TODO mozna by to jakos podzielic
        final Context context = getApplicationContext();
        final EventsQuery eventsQuery = getEventsQuery(context);
        final List<String> calendarIds = getCalendarIds(intent);
        final Optional<Cursor> cursor = queryForEvents(eventsQuery, intent, calendarIds);
        if (cursor.isPresent()) {
            final CalendarTimeZones calendarsTimeZones = getCalendarTimeZones(context, calendarIds);
            final List<CalendarEvent> calendarEvents =
                    this.calendarEventMapper.convertAll(cursor.get(), calendarsTimeZones);
            sendResult(intent.getParcelableExtra(Literals.RECEIVER), calendarEvents);
        }
    }

    EventsQuery getEventsQuery(final Context context) {
        return new EventsQuery(context);
    }

    private List<String> getCalendarIds(@NonNull final Intent intent) {
        return Arrays.asList(intent.getStringArrayExtra(Literals.CALENDAR_IDS));
    }

    void sendResult(final ResultReceiver resultReceiver,
                    final List<CalendarEvent> calendarEvents) {
        resultReceiver.send(RequestCodes.GET_EVENTS, prepareBundle(calendarEvents));
    }

    private CalendarTimeZones getCalendarTimeZones(final Context context,
                                                   final List<String> calendarIds) {
        // TODO mozna tu zrobic jakiegos cache'a
        return getCalendarsQuery(context).getCalendarsTimeZones(calendarIds);
    }

    private Optional<Cursor> queryForEvents(final EventsQuery eventsQuery, final Intent intent,
                                            final List<String> calendarIds) {
        final LocalDateTime firstDateTime = getFirstDateTime(intent);
        final LocalDateTime lastDateTime = getLastDateTime(intent, firstDateTime);
        return eventsQuery
                .query(calendarIds, firstDateTime, lastDateTime);
    }

    private LocalDateTime getFirstDateTime(@NonNull final Intent intent) {
        return LocalDateTime.parse(intent.getStringExtra(Literals.FIRST_DATE_TIME));
    }

    private LocalDateTime getLastDateTime(final Intent intent, final LocalDateTime firstDateTime) {
        if (intent.hasExtra(Literals.LAST_DATE_TIME)) {
            return LocalDateTime.parse(intent.getStringExtra(Literals.LAST_DATE_TIME));
        } else {
            return firstDateTime.plusWeeks(1);
        }
    }

    private Bundle prepareBundle(final List<CalendarEvent> calendarEvents) {
        final Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Literals.EVENTS, new ArrayList<>(calendarEvents));
        return bundle;
    }

    CalendarsQuery getCalendarsQuery(final Context context) {
        return new CalendarsQuery(context);
    }
}
