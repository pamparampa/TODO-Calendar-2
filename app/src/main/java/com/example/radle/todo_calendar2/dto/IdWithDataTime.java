package com.example.radle.todo_calendar2.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class IdWithDataTime {
    private final int id;
    private final LocalDateTime dateTime;
    private final boolean isToday;

    public IdWithDataTime(final int id, final LocalDateTime dateTime) {
        this(id, dateTime, false);
    }

    public IdWithDataTime(final int id, final LocalDateTime dateTime, final boolean isToday) {
        this.id = id;
        this.dateTime = dateTime;
        this.isToday = isToday;
    }

    public int getId() {
        return this.id;
    }

    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof IdWithDataTime)) return false;
        final IdWithDataTime that = (IdWithDataTime) o;
        return this.id == that.id &&
                Objects.equals(this.dateTime, that.dateTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(this.id, this.dateTime);
    }

    @Override
    public String toString() {
        return "IdWithDataTime{" +
                "id=" + this.id +
                ", dateTime=" + this.dateTime +
                '}';
    }

    public boolean isToday() {
        return this.isToday;
    }
}
