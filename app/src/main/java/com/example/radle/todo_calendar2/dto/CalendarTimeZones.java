package com.example.radle.todo_calendar2.dto;

import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class CalendarTimeZones {
    private final Map<String, ZoneId> map = new HashMap<>();

    public void add(final String calendarId, final ZoneId zoneId) {
        this.map.put(calendarId, zoneId);
    }

    public Set<String> getAllCalendarIds() {
        return this.map.keySet();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof CalendarTimeZones)) return false;
        final CalendarTimeZones that = (CalendarTimeZones) o;
        return Objects.equals(this.map, that.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.map);
    }

    @Override
    public String toString() {
        return "CalendarTimeZones{" +
                "map=" + this.map +
                '}';
    }

    public ZoneId get(final String index) {
        return this.map.get(index);
    }

    public boolean hasDataForId(final String calendarId) {
        return this.map.containsKey(calendarId);
    }
}
