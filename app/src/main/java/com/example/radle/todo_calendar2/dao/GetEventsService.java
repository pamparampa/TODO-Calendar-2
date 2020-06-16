package com.example.radle.todo_calendar2.dao;

import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.example.radle.todo_calendar2.calendarView.dto.CalendarEvent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

class GetEventsService extends JobIntentService {

    @Override
    protected void onHandleWork(@NonNull final Intent intent) {
        delay();
        final LocalDateTime firstDateTime =
                LocalDateTime.parse(intent.getStringExtra(CalendarEventsDao.FIRST_DATE_TIME));
        final ResultReceiver resultReceiver = intent.getParcelableExtra(CalendarEventsDao.RECEIVER);
        resultReceiver.send(CalendarEventsDao.GET_EVENTS, prepareBundle(firstDateTime));
    }

    private void delay() {
        try {
            Thread.sleep(5 * 1000);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Bundle prepareBundle(final LocalDateTime firstDateTime) {
        final Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(CalendarEventsDao.EVENTS, getCalendarEvents(firstDateTime));
        return bundle;
    }

    private ArrayList<CalendarEvent> getCalendarEvents(final LocalDateTime firstDateTime) {
        return new ArrayList<>(Arrays.asList(new CalendarEvent(
                String.valueOf(firstDateTime.getDayOfMonth()), firstDateTime,
                firstDateTime.plusHours(2))));
    }
}
