package com.example.radle.todo_calendar2.dao;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;

final class Utils {
    private static final int MILIS_IN_SECOND = 1000;
    private static final int NANOS_IN_MILI = 1000000;

    private Utils() {
    }

    static long getDatabaseFormatOfDate(final LocalDateTime date) {
        return getDatabaseFormatOfDate(date, TimeZone.getDefault().toZoneId());
    }

    static long getDatabaseFormatOfDate(final LocalDateTime date, final ZoneId zoneId) {
        final long epochSecond = date.toEpochSecond(
                zoneId.getRules().getOffset(date));
        return (epochSecond * MILIS_IN_SECOND) + (date.getNano() / NANOS_IN_MILI);
    }

    static LocalDateTime toLocalDate(final Long dtstart, final ZoneId zone) {
        return LocalDateTime.ofEpochSecond(
                Math.floorDiv(dtstart, MILIS_IN_SECOND),
                (int) Math.floorMod(dtstart, MILIS_IN_SECOND) * NANOS_IN_MILI,
                zone.getRules().getOffset(Instant.ofEpochMilli(dtstart)));
    }
}
