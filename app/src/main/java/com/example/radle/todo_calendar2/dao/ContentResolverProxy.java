package com.example.radle.todo_calendar2.dao;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

class ContentResolverProxy {
    private final ContentResolver contentResolver;

    ContentResolverProxy(final ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    Cursor query(final Uri uri, final String[] eventProjection, final String selection,
                 final String[] args, final String order) {
        return this.contentResolver.query(uri, eventProjection, selection, args, order);
    }
}
