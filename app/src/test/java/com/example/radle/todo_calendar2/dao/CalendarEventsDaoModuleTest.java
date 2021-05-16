package com.example.radle.todo_calendar2.dao;

import android.content.Context;
import android.content.Intent;

import com.example.radle.todo_calendar2.dto.CalendarEvent;
import com.google.common.collect.ImmutableList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CalendarEventsDaoModuleTest {
    private static final String LOCAL_ZONE = "Europe/Warsaw";
    private final LocalDateTime weekStartDate =
            LocalDateTime.of(1970, Month.JANUARY, 5, 0, 0);
    private final LocalDateTime weekEndDate =
            LocalDateTime.of(1970, Month.JANUARY, 12, 0, 0);
    private CalendarEventsDao calendarEventsDao;
    private ContentResolverStub<DbCalendarEvent> eventsContentResolverStub;
    private ContentResolverStub<DbCalendar> calendarsContentResolverStub;
    private GetEventsServiceStub
            getEventsServiceStub;

    @Before
    public void init() {
        final Context context = Mockito.mock(Context.class);
        this.eventsContentResolverStub = ContentResolverStub.of(DbCalendarEvent.class,
                CalendarEventCursorStub::new);
        this.calendarsContentResolverStub = ContentResolverStub.of(DbCalendar.class,
                CalendarCursorStub::new);

        Mockito.when(context.getContentResolver()).thenReturn(null);
        this.calendarEventsDao = new CalendarEventsDao(context) {
            @Override
            Intent createIntent() {
                return new IntentStub();
            }
        };
        this.getEventsServiceStub = new GetEventsServiceStub(
                context, this.eventsContentResolverStub,
                this.calendarsContentResolverStub);
        Whitebox.setInternalState(this.calendarEventsDao, "getEventsService",
                this.getEventsServiceStub);
    }

    private ImmutableList<DbCalendarEvent> prepareEventsData() {
        final DbCalendarEvent eventEndingInPrevWeek = new DbCalendarEvent.Builder()
                .withCalendarId("1")
                .withTitle("event1")
                .withDtStart(Utils.getDatabaseFormatOfDate(
                        this.weekStartDate.minusDays(1), ZoneId.of(LOCAL_ZONE)))
                .withDtEnd(Utils.getDatabaseFormatOfDate(
                        this.weekStartDate.minusHours(5), ZoneId.of(LOCAL_ZONE)))
                .withTimeZone(LOCAL_ZONE)
                .withDisplayColorIndex(1)
                .build();

        final DbCalendarEvent eventStartingInNextWeek = new DbCalendarEvent.Builder()
                .withCalendarId("2")
                .withTitle("event2")
                .withDtStart(Utils.getDatabaseFormatOfDate(
                        this.weekEndDate.plusHours(5), ZoneId.of(LOCAL_ZONE)))
                .withDtEnd(Utils.getDatabaseFormatOfDate(
                        this.weekEndDate.plusHours(10), ZoneId.of(LOCAL_ZONE)))
                .withTimeZone(LOCAL_ZONE)
                .withDisplayColorIndex(1)
                .build();

        final DbCalendarEvent eventInWeek = new DbCalendarEvent.Builder()
                .withCalendarId("1")
                .withTitle("event3")
                .withDtStart(Utils.getDatabaseFormatOfDate(
                        this.weekStartDate, ZoneId.of(LOCAL_ZONE)))
                .withDtEnd(Utils.getDatabaseFormatOfDate(
                        this.weekStartDate.plusHours(5), ZoneId.of(LOCAL_ZONE)))
                .withTimeZone(LOCAL_ZONE)
                .withDisplayColorIndex(1)
                .build();

        final DbCalendarEvent eventInWeekAndOtherCalendarId = new DbCalendarEvent.Builder()
                .withCalendarId("3")
                .withTitle("event4")
                .withDtStart(Utils.getDatabaseFormatOfDate(
                        this.weekStartDate, ZoneId.of(LOCAL_ZONE)))
                .withDtEnd(Utils.getDatabaseFormatOfDate(
                        this.weekStartDate.plusHours(5), ZoneId.of(LOCAL_ZONE)))
                .withTimeZone(LOCAL_ZONE)
                .withDisplayColorIndex(1)
                .build();

        final DbCalendarEvent eventLastingAllSunday = new DbCalendarEvent.Builder()
                .withCalendarId("1")
                .withTitle("event5")
                .withDtStart(Utils.getDatabaseFormatOfDate(
                        this.weekEndDate.minusDays(1), ZoneId.of(LOCAL_ZONE)))
                .withDtEnd(Utils.getDatabaseFormatOfDate(
                        this.weekEndDate, ZoneId.of(LOCAL_ZONE)))
                .withTimeZone(LOCAL_ZONE)
                .withDisplayColorIndex(1)
                .build();

        final DbCalendarEvent eventStartingInPrevWeek = new DbCalendarEvent.Builder()
                .withCalendarId("1")
                .withTitle("event6")
                .withDtStart(Utils.getDatabaseFormatOfDate(
                        this.weekStartDate.minusHours(1), ZoneId.of(LOCAL_ZONE)))
                .withDtEnd(Utils.getDatabaseFormatOfDate(
                        this.weekStartDate.plusHours(1), ZoneId.of(LOCAL_ZONE)))
                .withTimeZone(LOCAL_ZONE)
                .withDisplayColorIndex(1)
                .build();

        final DbCalendarEvent eventEndingInNextWeek = new DbCalendarEvent.Builder()
                .withCalendarId("1")
                .withTitle("event7")
                .withDtStart(Utils.getDatabaseFormatOfDate(
                        this.weekEndDate.minusHours(1), ZoneId.of(LOCAL_ZONE)))
                .withDtEnd(Utils.getDatabaseFormatOfDate(
                        this.weekEndDate.plusHours(1), ZoneId.of(LOCAL_ZONE)))
                .withTimeZone(LOCAL_ZONE)
                .withDisplayColorIndex(1)
                .build();

        return ImmutableList.of(
                eventEndingInPrevWeek, eventStartingInNextWeek, eventInWeek,
                eventInWeekAndOtherCalendarId, eventLastingAllSunday, eventStartingInPrevWeek,
                eventEndingInNextWeek);
    }

    private ImmutableList<DbCalendar> prepareCalendarsData() {
        return ImmutableList.of(new DbCalendar("1", LOCAL_ZONE));
    }

    private ImmutableList<CalendarEvent> prepareExpectedResults() {
        final CalendarEvent eventInWeek = new CalendarEvent(
                "event3", this.weekStartDate, this.weekStartDate.plusHours(5), 1);

        final CalendarEvent eventLastingAllSunday = new CalendarEvent(
                "event5", this.weekEndDate.minusDays(1), this.weekEndDate, 1);

        final CalendarEvent eventStartingInPrevWeek = new CalendarEvent(
                "event6", this.weekStartDate.minusHours(1), this.weekStartDate.plusHours(1), 1);

        final CalendarEvent eventEndingInNextWeek = new CalendarEvent(
                "event7", this.weekEndDate.minusHours(1), this.weekEndDate.plusHours(1), 1);

        return ImmutableList.of(
                eventInWeek,
                eventLastingAllSunday,
                eventStartingInPrevWeek,
                eventEndingInNextWeek);
    }

    @Test
    public void getEventsForWeek_shouldReturnEvent_IfItMeetsConstraints() {
        final List<DbCalendarEvent> eventsData = prepareEventsData();
        final List<DbCalendar> calendarsData = prepareCalendarsData();
        this.eventsContentResolverStub.setData(eventsData);
        this.calendarsContentResolverStub.setData(calendarsData);

        this.calendarEventsDao.getEventsForWeek(this.weekStartDate, Arrays.asList("1", "2"),
                calendarEvents -> {
                });
        final List<CalendarEvent> finalResult = this.getEventsServiceStub.getFinalResult();
        assertThat(finalResult).containsExactlyInAnyOrderElementsOf(prepareExpectedResults());
    }
}