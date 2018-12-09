package com.example.radle.todo_calendar2;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

final class DateTimesCollector {

    public static List<IdWithDataTime> collectForWeekRowView(final LocalDateTime dateTime) {
        final List<IdWithDataTime> idsWithDateTimes = new ArrayList<>();
        for (int hourId = 0; hourId < 24; hourId++) {
            idsWithDateTimes.add(new IdWithDataTime(hourId, dateTime.withHour(hourId)));
        }
        return idsWithDateTimes;
    }
}
