package com.example.radle.todo_calendar2.calendarView.tools;

import com.example.radle.todo_calendar2.calendarView.dto.IdWithDataTime;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class DateTimesCollector {

    public static List<IdWithDataTime> collectForWeekRowView(final LocalDateTime dateTime) {
        final List<IdWithDataTime> idsWithDateTimes = new ArrayList<>();
        for (int columnId = 0; columnId < 7; columnId++) {
            idsWithDateTimes.add(new IdWithDataTime(columnId, dateTime.plusDays(columnId)));
        }
        return idsWithDateTimes;
    }
}
