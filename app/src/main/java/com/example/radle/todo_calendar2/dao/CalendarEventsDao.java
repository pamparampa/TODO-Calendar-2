package com.example.radle.todo_calendar2.dao;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.example.radle.todo_calendar2.calendarView.dto.CalendarEvent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

public class CalendarEventsDao {
    private final Context context;
    private final GetEventsService getEventsService = new GetEventsService();

    public CalendarEventsDao(final Context context) {
        this.context = context;
    }

    public void getEventsForWeek(final LocalDateTime firstDateTime, final List<String> calendarIds,
                                 final Consumer<List<CalendarEvent>> consumer) {
        final Intent intent = prepareIntent(firstDateTime, calendarIds, consumer);
        this.getEventsService.enqueue(this.context, intent);
    }

    public void getEvents(final LocalDateTime from, final LocalDateTime to,
                          final List<String> calendarIds,
                          final Consumer<List<CalendarEvent>> consumer) {
        final Intent intent = prepareIntent(from, to, calendarIds, consumer);
        this.getEventsService.enqueue(this.context, intent);
    }

    private Intent prepareIntent(final LocalDateTime firstDateTime,
                                 final LocalDateTime lastDateTime,
                                 final List<String> calendarIds,
                                 final Consumer<List<CalendarEvent>> consumer) {
        final Intent intent = prepareIntent(firstDateTime, calendarIds, consumer);
        intent.putExtra(Literals.LAST_DATE_TIME, lastDateTime.toString());
        return intent;
    }

    private Intent prepareIntent(final LocalDateTime firstDateTime,
                                 final List<String> calendarIds,
                                 final Consumer<List<CalendarEvent>> consumer) {
        final Intent intent = createIntent();
        intent.putExtra(Literals.FIRST_DATE_TIME, firstDateTime.toString());
        intent.putExtra(Literals.RECEIVER, new EventsReceiver(new Handler(), consumer));
        intent.putExtra(Literals.CALENDAR_IDS, calendarIds.toArray(new String[0]));
        return intent;
    }

    Intent createIntent() {
        return new Intent(this.context, GetEventsService.class);
    }
}
