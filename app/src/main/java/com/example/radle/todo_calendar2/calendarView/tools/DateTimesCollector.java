package com.example.radle.todo_calendar2.calendarView.tools;

import com.example.radle.todo_calendar2.calendarView.TimeNotAlignedException;
import com.example.radle.todo_calendar2.dto.IdWithDataTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class DateTimesCollector {

    public List<IdWithDataTime> collectForTopLabelRow(final LocalDateTime firstDateTime) throws TimeNotAlignedException {
        if (new DateTimeAlignmentChecker().isTimeNotAlignedForWeekColumn(firstDateTime))
            throw new TimeNotAlignedException(ChronoUnit.WEEKS);
        return collectForWeekRow(firstDateTime);
    }

    public List<IdWithDataTime> collectForWeekRow(final LocalDateTime firstDateTime) throws TimeNotAlignedException {
        if (new DateTimeAlignmentChecker().isTimeNotAlignedForWeekRow(firstDateTime))
            throw new TimeNotAlignedException(ChronoUnit.HOURS);
        final List<IdWithDataTime> idsWithDateTimes = new ArrayList<>();
        for (int columnId = 0; columnId < 7; columnId++)
            idsWithDateTimes.add(createIdWithDateTime(columnId, firstDateTime));
        return idsWithDateTimes;
    }

    private IdWithDataTime createIdWithDateTime(final int columnId,
                                                final LocalDateTime firstDateTime) {
        final LocalDateTime currentDateTime = firstDateTime.plusDays(columnId);
        return new IdWithDataTime(columnId, currentDateTime,
                LocalDate.now().isEqual(currentDateTime.toLocalDate()));
    }

    public List<IdWithDataTime> collectRowsForWeek(final LocalDateTime firstDateTime) throws TimeNotAlignedException {
        if (new DateTimeAlignmentChecker().isTimeNotAlignedForWeekColumn(firstDateTime)) {
            throw new TimeNotAlignedException(ChronoUnit.WEEKS);
        }
        final List<IdWithDataTime> idsWithDateTimes = new ArrayList<>();
        for (int hourId = 0; hourId < 24; hourId++) {
            idsWithDateTimes.add(new IdWithDataTime(hourId, firstDateTime.withHour(hourId)));
        }
        return idsWithDateTimes;
    }
}
