package com.example.radle.todo_calendar2.dao;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.example.radle.todo_calendar2.dto.Calendar;

import java.util.List;
import java.util.function.Consumer;

public class CalendarsDao {
    private final Context context;
    private final GetCalendarsService getCalendarsService = new GetCalendarsService();

    public CalendarsDao(final Context context) {
        this.context = context;
    }

    public void getAllCalendars(final Consumer<List<Calendar>> consumer) {
        final Intent intent = prepareIntent(consumer);
        this.getCalendarsService.enqueue(this.context, intent);
    }

    private Intent prepareIntent(Consumer<List<Calendar>> consumer) {
        final Intent intent = new Intent(this.context, GetCalendarsService.class);
        intent.putExtra(Literals.RECEIVER, new CalendarsReceiver(new Handler(), consumer));
        return intent;
    }

}
