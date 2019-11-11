package com.example.radle.todo_calendar2.calendarView.tools;

import com.example.radle.todo_calendar2.calendarView.TimeNotAlignedException;
import com.example.radle.todo_calendar2.calendarView.dto.IdWithDataTime;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class DateTimesCollector {

    public List<IdWithDataTime> collectForTopLabelRow(final LocalDateTime firstDateTime) throws TimeNotAlignedException {
        if (isTimeNotAlignedForWeekColumn(firstDateTime)) {
            throw new TimeNotAlignedException(ChronoUnit.WEEKS);
        }
        return collectForWeekRow(firstDateTime);
    }

    public List<IdWithDataTime> collectForWeekRow(final LocalDateTime firstDateTime) throws TimeNotAlignedException {
        if (isTimeNotAlignedForWeekRow(firstDateTime)) {
            throw new TimeNotAlignedException(ChronoUnit.HOURS);
        }
        final List<IdWithDataTime> idsWithDateTimes = new ArrayList<>();
        for (int columnId = 0; columnId < 7; columnId++) {
            final LocalDateTime currentDateTime = firstDateTime.plusDays(columnId);
            idsWithDateTimes.add(new IdWithDataTime(columnId, currentDateTime,
                    LocalDate.now().isEqual(currentDateTime.toLocalDate())));
        }
        return idsWithDateTimes;
    }

    private boolean isTimeNotAlignedForWeekRow(final LocalDateTime dateTime) {
        return !dateTime.equals(dateTime.toLocalDate().atTime(dateTime.getHour(), 0));
    }

    public List<IdWithDataTime> collectForWeekColumn(final LocalDateTime firstDateTime) throws TimeNotAlignedException {
        if (isTimeNotAlignedForWeekColumn(firstDateTime)) {
            throw new TimeNotAlignedException(ChronoUnit.WEEKS);
        }
        final List<IdWithDataTime> idsWithDateTimes = new ArrayList<>();
        for (int hourId = 0; hourId < 24; hourId++) {
            idsWithDateTimes.add(new IdWithDataTime(hourId, firstDateTime.withHour(hourId)));
        }
        return idsWithDateTimes;
    }

    private boolean isTimeNotAlignedForWeekColumn(final LocalDateTime firstDateTime) {
        return !firstDateTime.isEqual(firstDateTime.toLocalDate().atStartOfDay()) || firstDateTime
                .getDayOfWeek() != DayOfWeek.MONDAY;
    }
}
