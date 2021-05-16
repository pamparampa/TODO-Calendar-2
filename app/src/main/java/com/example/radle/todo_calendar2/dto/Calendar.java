package com.example.radle.todo_calendar2.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class Calendar implements Parcelable {
    private final String id;
    private final String title;

    public Calendar(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
    }

    public Calendar(String id, String title) {
        this.id = id;
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.title);
    }

    public static final Creator<Calendar> CREATOR = new Creator<Calendar>() {
        @Override
        public Calendar createFromParcel(final Parcel in) {
            return new Calendar(in);
        }

        @Override
        public Calendar[] newArray(final int size) {
            return new Calendar[size];
        }
    };

    public String getCalendarId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
