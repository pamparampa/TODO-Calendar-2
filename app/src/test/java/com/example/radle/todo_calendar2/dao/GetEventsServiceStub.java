package com.example.radle.todo_calendar2.dao;

import android.content.Context;
import android.content.Intent;
import android.os.ResultReceiver;

import com.example.radle.todo_calendar2.calendarView.dto.CalendarEvent;

import java.util.List;

public class GetEventsServiceStub extends GetEventsService {

    private final Context context;
    private final ContentResolverStub eventsContentResolverStub;
    private final ContentResolverStub calendarsContentResolverStub;
    private List<CalendarEvent> finalResult;

    public GetEventsServiceStub(final Context context,
                                final ContentResolverStub eventsContentResolverStub,
                                final ContentResolverStub calendarsContentResolverStub) {
        this.context = context;
        this.eventsContentResolverStub = eventsContentResolverStub;
        this.calendarsContentResolverStub = calendarsContentResolverStub;
    }

    @Override
    public void enqueue(final Context context, final Intent intent) {
        onHandleWork(intent);
    }

    @Override
    public Context getApplicationContext() {
        return this.context;
    }

    @Override
    CalendarsQuery getCalendarsQuery(final Context context) {
        return new CalendarsQueryStub(context, this.calendarsContentResolverStub);
    }

    @Override
    EventsQuery getEventsQuery(final Context context) {
        return new EventsQueryStub(context, this.eventsContentResolverStub);
    }

    @Override
    void sendResult(final ResultReceiver resultReceiver,
                    final List<CalendarEvent> calendarEvents) {
        this.finalResult = calendarEvents;
    }

    public List<CalendarEvent> getFinalResult() {
        return this.finalResult;
    }
}