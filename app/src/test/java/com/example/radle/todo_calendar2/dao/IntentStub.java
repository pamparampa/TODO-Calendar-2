package com.example.radle.todo_calendar2.dao;

import android.content.Intent;
import android.os.Parcelable;
import android.os.ResultReceiver;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class IntentStub extends Intent {

    private final Map<String, String> strings = new HashMap<>();
    private final Map<String, String[]> arrays = new HashMap<>();
    private ResultReceiver receiver;

    @NonNull
    @Override
    public Intent putExtra(final String name, final String value) {
        this.strings.put(name, value);
        return this;
    }

    @NonNull
    @Override
    public Intent putExtra(final String name, final String[] value) {
        this.arrays.put(name, value);
        return this;
    }

    @NonNull
    @Override
    public Intent putExtra(final String name, final Parcelable value) {
        if (name.equals("receiver")) {
            this.receiver = (ResultReceiver) value;
        }
        return this;
    }

    @Override
    public String[] getStringArrayExtra(final String name) {
        return this.arrays.get(name);
    }

    @Override
    public String getStringExtra(final String name) {
        return this.strings.get(name);
    }

    @Override
    public boolean hasExtra(final String name) {
        return this.strings.containsKey(name) || this.arrays.containsKey(name);
    }

    @Override
    public Parcelable getParcelableExtra(final String name) {
        return this.receiver;
    }

}