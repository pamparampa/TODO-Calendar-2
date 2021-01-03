package com.example.radle.todo_calendar2.dao;

import android.content.Context;

class CalendarsQueryWithoutPermissionCheck extends CalendarsQuery {

    CalendarsQueryWithoutPermissionCheck(final Context context) {
        super(context);
    }

    @Override
    void checkPermission() {
    }
}
