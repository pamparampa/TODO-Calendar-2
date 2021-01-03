package com.example.radle.todo_calendar2.dao;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import com.example.radle.todo_calendar2.calendarView.dto.CalendarEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EventsReceiver extends ResultReceiver {
    private final Consumer<List<CalendarEvent>> consumer;

    EventsReceiver(final Handler handler, final Consumer<List<CalendarEvent>> consumer) {
        super(handler);
        this.consumer = consumer;
    }

    @Override
    protected void onReceiveResult(final int resultCode, final Bundle resultData) {
        if (resultCode == Literals.GET_EVENTS) {
            final ArrayList<CalendarEvent> events =
                    resultData.getParcelableArrayList(Literals.EVENTS);
            this.consumer.accept(events);
        }
    }


}