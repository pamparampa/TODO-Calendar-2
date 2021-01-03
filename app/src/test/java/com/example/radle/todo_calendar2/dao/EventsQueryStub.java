package com.example.radle.todo_calendar2.dao;

import android.content.Context;

class EventsQueryStub extends EventsQuery {

    private final ContentResolverProxy eventsContentResolver;

    EventsQueryStub(final Context context, final ContentResolverProxy eventsContentResolver) {
        super(context);
        this.eventsContentResolver = eventsContentResolver;
    }

    @Override
    ContentResolverProxy getContentResolver(final Context context) {
        return this.eventsContentResolver;
    }

    @Override
    void checkPermission() {
    }

}