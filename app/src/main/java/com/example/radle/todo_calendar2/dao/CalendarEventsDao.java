package com.example.radle.todo_calendar2.dao;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import com.example.radle.todo_calendar2.calendarView.dto.CalendarEvent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CalendarEventsDao {
    static final int GET_EVENTS = 111;
    static final String EVENTS = "events";
    static final String FIRST_DATE_TIME = "firstDateTime";
    static final String LAST_DATE_TIME = "lastDateTime";
    static final String RECEIVER = "receiver";
    static final String CALENDAR_IDS = "calendarIds";

    private final Context context;

    public CalendarEventsDao(final Context context) {
        this.context = context;
    }

    public void getEventsForWeek(final LocalDateTime firstDateTime, final List<String> calendarIds,
                                 final Consumer<List<CalendarEvent>> consumer) {
        final Intent intent = prepareIntent(firstDateTime, calendarIds, consumer);
        GetEventsService.enqueueWork(this.context, GetEventsService.class, 1, intent);
    }

    public void getEvents(final LocalDateTime from, final LocalDateTime to,
                          final List<String> calendarIds,
                          final Consumer<List<CalendarEvent>> consumer) {
        final Intent intent = prepareIntent(from, to, calendarIds, consumer);
        GetEventsService.enqueueWork(this.context, GetEventsService.class, 1, intent);
    }

    private Intent prepareIntent(final LocalDateTime firstDateTime,
                                 final LocalDateTime lastDateTime,
                                 final List<String> calendarIds,
                                 final Consumer<List<CalendarEvent>> consumer) {
        final Intent intent = prepareIntent(firstDateTime, calendarIds, consumer);
        intent.putExtra(LAST_DATE_TIME, lastDateTime.toString());
        return intent;
    }

    private Intent prepareIntent(final LocalDateTime firstDateTime,
                                 final List<String> calendarIds,
                                 final Consumer<List<CalendarEvent>> consumer) {
        final Intent intent = new Intent(this.context, GetEventsService.class);
        intent.putExtra(FIRST_DATE_TIME, firstDateTime.toString());
        intent.putExtra(RECEIVER, new EventsReceiver(new Handler(), consumer));
        intent.putExtra(CALENDAR_IDS, calendarIds.toArray());
        return intent;
    }

    private class EventsReceiver extends ResultReceiver {
        private final Consumer<List<CalendarEvent>> consumer;

        EventsReceiver(final Handler handler, final Consumer<List<CalendarEvent>> consumer) {
            super(handler);
            this.consumer = consumer;
        }

        @Override
        protected void onReceiveResult(final int resultCode, final Bundle resultData) {
            if (resultCode == GET_EVENTS) {
                final ArrayList<CalendarEvent> events = resultData.getParcelableArrayList(EVENTS);
                this.consumer.accept(events);
            }
        }
    }
}
