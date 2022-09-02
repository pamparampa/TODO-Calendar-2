package com.example.radle.todo_calendar2.dao;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import com.example.radle.todo_calendar2.constant.RequestCodes;
import com.example.radle.todo_calendar2.dto.Calendar;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CalendarsReceiver extends ResultReceiver {
    private final Consumer<List<Calendar>> consumer;

    public CalendarsReceiver(Handler handler, Consumer<List<Calendar>> consumer) {
        super(handler);
        this.consumer = consumer;
    }

    @Override
    protected void onReceiveResult(final int resultCode, final Bundle resultData) {
        if (resultCode == RequestCodes.GET_CALENDARS_CODE) {
            final ArrayList<Calendar> calendars =
                    resultData.getParcelableArrayList(Literals.CALENDARS);
            this.consumer.accept(calendars);
        }
    }
}
