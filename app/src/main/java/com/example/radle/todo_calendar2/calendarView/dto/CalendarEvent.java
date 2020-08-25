package com.example.radle.todo_calendar2.calendarView.dto;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDateTime;
import java.util.Objects;

public class CalendarEvent implements Parcelable {
    private final String title;
    final LocalDateTime startTime;
    final LocalDateTime endTime;
    private final long color;


    public CalendarEvent(final String title, final LocalDateTime startTime,
                         final LocalDateTime endTime) {
        this(title, startTime, endTime, Color.BLUE);
    }

    public CalendarEvent(final String title, final LocalDateTime startTime,
                         final LocalDateTime endTime, final long color) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.color = color;
    }

    protected CalendarEvent(final Parcel in) {
        this.title = in.readString();
        this.startTime = LocalDateTime.parse(in.readString());
        this.endTime = LocalDateTime.parse(in.readString());
        this.color = Color.BLUE;
    }

    public static final Creator<CalendarEvent> CREATOR = new Creator<CalendarEvent>() {
        @Override
        public CalendarEvent createFromParcel(final Parcel in) {
            return new CalendarEvent(in);
        }

        @Override
        public CalendarEvent[] newArray(final int size) {
            return new CalendarEvent[size];
        }
    };

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof CalendarEvent)) return false;
        final CalendarEvent that = (CalendarEvent) o;
        return Objects.equals(this.title, that.title) &&
                Objects.equals(this.startTime, that.startTime) &&
                Objects.equals(this.endTime, that.endTime) &&
                Objects.equals(this.color, that.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.title, this.startTime, this.endTime, this.color);
    }

    @Override
    public String toString() {
        return "CalendarEvent{" +
                "title='" + this.title + '\'' +
                ", startTime=" + this.startTime +
                ", endTime=" + this.endTime +
                ", color=" + this.color +
                '}';
    }

    public String getTitle() {
        return this.title;
    }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    public CalendarEvent getCalendarEvent() {
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int i) {
        parcel.writeString(this.title);
        parcel.writeString(this.startTime.toString());
        parcel.writeString(this.endTime.toString());
    }
}
