package com.example.radle.todo_calendar2.calendarView.dto;

import android.database.Cursor;

import com.example.radle.todo_calendar2.dto.CalendarEvent;
import com.example.radle.todo_calendar2.dto.CalendarEventMapper;
import com.example.radle.todo_calendar2.dto.CalendarTimeZones;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

public class CalendarEventMapperTest {

    private final int colorCode = 100;
    private final CalendarEventMapper subject = new CalendarEventMapper();
    private final LocalDateTime earlierDate =
            LocalDateTime.of(2020, Month.JUNE, 29, 0, 0);
    private final String earlierDateInEpochMilis = "1593381600000";
    private final LocalDateTime laterDate =
            LocalDateTime.of(2020, Month.JULY, 6, 0, 0);
    private final String laterDateInEpochMilis = "1593986400000";

    @Test
    public void convertAll_shouldReturnEmptyList_whenCursorIsEmpty() {
        final Cursor cursor = new CursorStub(Collections.emptyList());
        assertTrue(this.subject.convertAll(cursor, new CalendarTimeZones()).isEmpty());
    }

    @Test
    public void convertAll_shouldReturnCalendarEventWithItsOwnTimezone_whenEventTimeZoneIsNotZero() {
        final Cursor cursor = new CursorStub(Collections.singletonList(
                Arrays.asList("1", "event", this.earlierDateInEpochMilis,
                        this.laterDateInEpochMilis, "Europe/Warsaw", "Asia/Tokyo",
                        String.valueOf(this.colorCode))
        ));

        final List<CalendarEvent> calendarEvents =
                this.subject.convertAll(cursor, new CalendarTimeZones());

        assertThat(calendarEvents).containsOnly(new CalendarEvent("event", this.earlierDate,
                this.laterDate.withHour(7), this.colorCode));
    }

    @Test
    public void convertAll_shouldReturnCalendarEventWithTimeZoneFromCalendar_whenEventTimeZoneIsZero() {
        final Cursor cursor = new CursorStub(Collections.singletonList(
                Arrays.asList("1", "event", this.earlierDateInEpochMilis,
                        this.laterDateInEpochMilis,
                        "0", "0", String.valueOf(this.colorCode))));

        final CalendarTimeZones calendarTimeZones = new CalendarTimeZones();
        calendarTimeZones.add("1", ZoneId.of("Europe/Warsaw"));
        final List<CalendarEvent> calendarEvents =
                this.subject.convertAll(cursor, calendarTimeZones);

        assertThat(calendarEvents).containsOnly(new CalendarEvent("event", this.earlierDate,
                this.laterDate, this.colorCode));
    }

    @Test
    public void convertAll_shouldReturnFewCalendarEvents_whenFewRecordsInCursor() {
        final Cursor cursor = new CursorStub(Arrays.asList(
                Arrays.asList("1", "event1", this.earlierDateInEpochMilis,
                        String.valueOf(Long.parseLong(this.earlierDateInEpochMilis) + 100),
                        "0", "0", String.valueOf(this.colorCode)),
                Arrays.asList("2", "event2", this.laterDateInEpochMilis,
                        String.valueOf(Long.parseLong(this.laterDateInEpochMilis) + 100),
                        "0", "0", String.valueOf(this.colorCode))));
        final CalendarTimeZones calendarTimeZones = new CalendarTimeZones();
        calendarTimeZones.add("1", ZoneId.of("Europe/Warsaw"));
        calendarTimeZones.add("2", ZoneId.of("Asia/Tokyo"));
        final List<CalendarEvent> calendarEvents =
                this.subject.convertAll(cursor, calendarTimeZones);

        assertThat(calendarEvents).containsExactly(
                new CalendarEvent("event1", this.earlierDate, this.earlierDate.withNano(100000000),
                        this.colorCode),
                new CalendarEvent("event2", this.laterDate.withHour(7),
                        this.laterDate.withHour(7).withNano(100000000),
                        this.colorCode));
    }
}