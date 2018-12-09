package com.example.radle.todo_calendar2.calendarView.tools;

import com.example.radle.todo_calendar2.calendarView.dto.IdWithDataTime;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class DateTimesCollector {

    public static List<IdWithDataTime> collectForWeekRowView(final LocalDateTime dateTime) {
        final List<IdWithDataTime> idsWithDateTimes = new ArrayList<>();
        for (int hourId = 0; hourId < 24; hourId++) {
            idsWithDateTimes.add(new IdWithDataTime(hourId, dateTime.withHour(hourId)));
        }
        return idsWithDateTimes;
    }
}
