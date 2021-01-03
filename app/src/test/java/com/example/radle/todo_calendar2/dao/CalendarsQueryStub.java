package com.example.radle.todo_calendar2.dao;

import android.content.Context;

class CalendarsQueryStub extends CalendarsQuery {
    private final ContentResolverProxy calendarsContentResolver;

    CalendarsQueryStub(final Context context,
                       final ContentResolverProxy calendarsContentResolver) {
        super(context);
        this.calendarsContentResolver = calendarsContentResolver;
    }

    @Override
    ContentResolverProxy getContentResolver(final Context context) {
        return this.calendarsContentResolver;
    }

    @Override
    void checkPermission() {
    }
}