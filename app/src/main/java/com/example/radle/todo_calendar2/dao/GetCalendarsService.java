package com.example.radle.todo_calendar2.dao;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.ResultReceiver;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.example.radle.todo_calendar2.constant.RequestCodes;
import com.example.radle.todo_calendar2.dto.Calendar;
import com.example.radle.todo_calendar2.dto.CalendarMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GetCalendarsService extends JobIntentService {
    private final CalendarMapper calendarMapper = new CalendarMapper();

    public void enqueue(final Context context, final Intent intent) {
        enqueueWork(context, GetCalendarsService.class, 1, intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        final Context context = getApplicationContext();
        final CalendarsQuery calendarsQuery = getCalendarsQuery(context);
        Optional<Cursor> calendarsCursor = calendarsQuery.getAllCalendars();
        if (calendarsCursor.isPresent()) {
            final List<Calendar> calendars = this.calendarMapper.convertAll(calendarsCursor.get());
            sendResult(intent.getParcelableExtra(Literals.RECEIVER), calendars);
        }
    }

    private CalendarsQuery getCalendarsQuery(Context context) {
        return new CalendarsQuery(context);
    }

    private void sendResult(ResultReceiver resultReceiver, List<Calendar> calendars) {
        resultReceiver.send(RequestCodes.GET_CALENDARS_CODE, prepareBundle(calendars));
    }

    private Bundle prepareBundle(List<Calendar> calendars) {
        final Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Literals.CALENDARS, new ArrayList<>(calendars));
        return bundle;
    }
}
