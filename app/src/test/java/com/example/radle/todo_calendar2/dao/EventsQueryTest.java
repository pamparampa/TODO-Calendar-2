package com.example.radle.todo_calendar2.dao;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.CalendarContract;

import com.example.radle.todo_calendar2.calendarView.dto.CalendarEventMapper;
import com.example.radle.todo_calendar2.calendarView.dto.CalendarTimeZones;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertFalse;

public class EventsQueryTest {

    private final LocalDateTime startDate =
            LocalDateTime.of(2020, Month.JUNE, 29, 0, 0);
    private final String startDateInEpochMilis = "1593381600000";
    private final LocalDateTime endDate =
            LocalDateTime.of(2020, Month.JULY, 6, 0, 0);
    private final String endDateInEpochMilis = "1593986400000";
    private ContentResolver contentResolver;
    private EventsQuery subject;
    private final CalendarTimeZones calendarTimeZones = new CalendarTimeZones();

    @Before
    public void init() {
        this.contentResolver = Mockito.mock(ContentResolver.class);
        this.calendarTimeZones.add("1", ZoneId.of("Europe/Warsaw"));
        final Context context = Mockito.mock(Context.class);
        Mockito.when(context.getContentResolver()).thenReturn(this.contentResolver);
        this.subject = new EventsQueryWithoutPermissionCheck(context);
    }

    @Test
    public void query_shouldNotQueryInContentResolver_whenEmptyCalendarListPassed() {
        assertFalse(this.subject.query(Collections.emptyList(), this.startDate, this.endDate)
                .isPresent());
        Mockito.verifyZeroInteractions(this.contentResolver);
    }

    @Test
    public void query_shouldMakeQueryWithThreeParameters_whenOneCalendarPassed() {
        final Cursor cursor = new CursorStub();
        Mockito.when(
                this.contentResolver.query(CalendarContract.Events.CONTENT_URI,
                        CalendarEventMapper.EVENT_PROJECTION,
                        CalendarContract.Events.CALENDAR_ID + " IN (?) " +
                                "AND " + CalendarContract.Events.DTSTART + " >= ? " +
                                "AND " + CalendarContract.Events.DTEND + " <= ?",
                        new String[]{"1", this.startDateInEpochMilis, this.endDateInEpochMilis},
                        null)
        ).thenReturn(cursor);

        Assert.assertEquals(Optional.of(cursor),
                this.subject.query(Collections.singletonList("1"), this.startDate, this.endDate));
    }

    @Test
    public void query_shouldMakeQueryWithFiveParameters_WhenThreeCalendarsPassed() {
        final Cursor cursor = new CursorStub();
        Mockito.when(
                this.contentResolver.query(CalendarContract.Events.CONTENT_URI,
                        CalendarEventMapper.EVENT_PROJECTION,
                        CalendarContract.Events.CALENDAR_ID + " IN (?,?,?) " +
                                "AND " + CalendarContract.Events.DTSTART + " >= ? " +
                                "AND " + CalendarContract.Events.DTEND + " <= ?",
                        new String[]{"1", "2", "3", this.startDateInEpochMilis,
                                this.endDateInEpochMilis},
                        null)
        ).thenReturn(cursor);

        Assert.assertEquals(Optional.of(cursor),
                this.subject.query(Arrays.asList("1", "2", "3"), this.startDate, this.endDate));
    }


    private class EventsQueryWithoutPermissionCheck extends EventsQuery {
        EventsQueryWithoutPermissionCheck(final Context context) {
            super(context);
        }

        @Override
        void checkPermission() {
        }
    }


}