package com.example.radle.todo_calendar2.dao;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.CalendarContract;

import com.example.radle.todo_calendar2.calendarView.dto.CalendarEventMapper;
import com.example.radle.todo_calendar2.calendarView.dto.CalendarTimeZones;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CalendarsQueryTest {

    private static final String ZONE_ID = "Europe/Warsaw";
    private ContentResolver contentResolver;
    private Cursor cursor;
    private CalendarsQuery subject;

    public CalendarsQueryTest() {
    }

    @Before
    public void init() {
        this.contentResolver = Mockito.mock(ContentResolver.class);
        this.cursor = Mockito.mock(
                Cursor.class); //  TODO mozna przerobic na stuba tak jak w ClandarEventMapperTest
        final Context context = Mockito.mock(Context.class);
        Mockito.when(context.getContentResolver()).thenReturn(this.contentResolver);
        this.subject = new CalendarsQueryWithoutPermissionCheck(context);
    }

    @Test
    public void getCalendarsTimeZones_shouldReturnEmptyObject_whenEmptyCalendarIdsPassed() {
        final CalendarTimeZones calendarsTimeZones =
                this.subject.getCalendarsTimeZones(Collections.emptyList());
        assertTrue(calendarsTimeZones.getAllCalendarIds().isEmpty());
        Mockito.verifyZeroInteractions(this.contentResolver);
    }

    @Test
    public void getCalendarsTimeZones_shouldReturnSomeTimeZones_whenSomeCalendarsPassed() {
        Mockito.when(this.contentResolver.query(CalendarContract.Calendars.CONTENT_URI,
                CalendarEventMapper.CALENDAR_PROJECTION,
                CalendarContract.Calendars._ID + " IN (?,?)",
                new String[]{"1", "2"},
                null)
        ).thenReturn(this.cursor);
        Mockito.when(this.cursor.moveToNext()).thenReturn(true, true, false);
        Mockito.when(this.cursor.getString(CalendarEventMapper.CALENDAR_ID_INDEX))
                .thenReturn("1", "2");
        Mockito.when(this.cursor.getString(CalendarEventMapper.CALENDAR_ZONE_INDEX))
                .thenReturn(ZONE_ID, ZONE_ID);

        final CalendarTimeZones expectedCalendarTimeZones = new CalendarTimeZones();
        expectedCalendarTimeZones.add("1", ZoneId.of(ZONE_ID));
        expectedCalendarTimeZones.add("2", ZoneId.of(ZONE_ID));

        assertEquals(expectedCalendarTimeZones, this.subject.getCalendarsTimeZones(
                Arrays.asList("1", "2")));

    }

    private class CalendarsQueryWithoutPermissionCheck extends CalendarsQuery {

        CalendarsQueryWithoutPermissionCheck(final Context context) {
            super(context);
        }

        @Override
        void checkPermission() {
        }
    }

}