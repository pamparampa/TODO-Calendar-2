package com.example.radle.todo_calendar2.dao;

public class DbCalendarEvent {
    private String calendar_id;
    private String event_id;
    private long dtStart;
    private long dtEnd;
    private String title;
    private String localZone;
    private String endTimeZone;
    private int displayColor;

    public DbCalendarEvent(final String calendar_id, final String event_id, final int startTime, final int endTime) {
        this.calendar_id = calendar_id;
        this.event_id = event_id;
        this.dtStart = startTime;
        this.dtEnd = endTime;
    }

    public String calendar_id() {
        return this.calendar_id;
    }

    public String event_id() {
        return this.event_id;
    }

    public long dtstart() {
        return this.dtStart;
    }

    public long dtend() {
        return this.dtEnd;
    }

    public String title() {
        return this.title;
    }

    public String localZone() {
        return this.localZone;
    }

    public String endTimeZone() {
        return this.endTimeZone;
    }

    public int displayColor() {
        return this.displayColor;
    }

    @Override
    public String toString() {
        return "DbCalendarEvent{" +
                "calendar_Id='" + this.calendar_id + '\'' +
                ", dtStart=" + this.dtStart +
                ", dtEnd=" + this.dtEnd +
                ", title='" + this.title + '\'' +
                ", localZone='" + this.localZone + '\'' +
                ", displayColor=" + this.displayColor +
                '}';
    }

    static class Builder {
        DbCalendarEvent dbCalendarEvent = new DbCalendarEvent("1", "1", 1, 1);

        Builder withEventId(final String eventId) {
            this.dbCalendarEvent.event_id = eventId;
            return this;
        }

        Builder withCalendarId(final String calendarId) {
            this.dbCalendarEvent.calendar_id = calendarId;
            return this;
        }

        Builder withTitle(final String title) {
            this.dbCalendarEvent.title = title;
            return this;
        }

        Builder withDtStart(final long dtStart) {
            this.dbCalendarEvent.dtStart = dtStart;
            return this;
        }

        Builder withDtEnd(final long dtEnd) {
            this.dbCalendarEvent.dtEnd = dtEnd;
            return this;
        }

        Builder withTimeZone(final String localZone) {
            this.dbCalendarEvent.localZone = localZone;
            return this;
        }

        Builder withDisplayColorIndex(final int displayColor) {
            this.dbCalendarEvent.displayColor = displayColor;
            return this;
        }

        DbCalendarEvent build() {
            return this.dbCalendarEvent;
        }
    }
}
