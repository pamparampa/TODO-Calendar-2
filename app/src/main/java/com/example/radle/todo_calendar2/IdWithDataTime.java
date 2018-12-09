package com.example.radle.todo_calendar2;

import java.time.LocalDateTime;
import java.util.Objects;

class IdWithDataTime {
    private final int id;
    private final LocalDateTime dateTime;

    public IdWithDataTime(final int id, final LocalDateTime dateTime) {
        this.id = id;
        this.dateTime = dateTime;
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
}
