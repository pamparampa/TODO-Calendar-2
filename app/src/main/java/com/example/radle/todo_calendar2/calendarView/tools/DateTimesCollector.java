package com.example.radle.todo_calendar2.calendarView.tools;

import com.example.radle.todo_calendar2.calendarView.TimeNotAlignedException;
import com.example.radle.todo_calendar2.calendarView.dto.IdWithDataTime;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public final class DateTimesCollector {

    public static List<IdWithDataTime> collectForWeekRowView(final LocalDateTime dateTime) throws TimeNotAlignedException {
        if (isTimeNotAlignedForWeekRowView(dateTime)) {
            throw new TimeNotAlignedException(ChronoUnit.HOURS);
        }
        final List<IdWithDataTime> idsWithDateTimes = new ArrayList<>();
        for (int columnId = 0; columnId < 7; columnId++) {
            idsWithDateTimes.add(new IdWithDataTime(columnId, dateTime.plusDays(columnId)));
        }
        return idsWithDateTimes;
    }

    private static boolean isTimeNotAlignedForWeekRowView(final LocalDateTime dateTime) {
        return !dateTime.equals(dateTime.toLocalDate().atTime(dateTime.getHour(), 0));
    }

    public static List<IdWithDataTime> collectForBoardListView(final LocalDateTime dateTime) throws TimeNotAlignedException {
        if (isTimeNotAlignedForBoardListView(dateTime)) {
            throw new TimeNotAlignedException(ChronoUnit.WEEKS);
        }
        final List<IdWithDataTime> idsWithDateTimes = new ArrayList<>();
        for (int hourId = 0; hourId < 24; hourId++) {
            idsWithDateTimes.add(new IdWithDataTime(hourId, dateTime.withHour(hourId)));
        }
        return idsWithDateTimes;
    }

    private static boolean isTimeNotAlignedForBoardListView(final LocalDateTime dateTime) {
        return !dateTime.isEqual(dateTime.toLocalDate().atStartOfDay()) || dateTime.getDayOfWeek() != DayOfWeek.MONDAY;
    }
}
