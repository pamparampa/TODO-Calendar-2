package com.example.radle.todo_calendar2;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


public class DateTimesCollectorTest {
    private static final LocalDateTime DATE_TIME = LocalDateTime.of(LocalDate.now(), LocalTime.of
            (0, 0));
    private static final IdWithDataTime[] EXPECTED_IDS_WITH_DATE_TIMES = {
            new IdWithDataTime(0, DATE_TIME.withHour(0)),
            new IdWithDataTime(1, DATE_TIME.withHour(1)),
            new IdWithDataTime(2, DATE_TIME.withHour(2)),
            new IdWithDataTime(3, DATE_TIME.withHour(3)),
            new IdWithDataTime(4, DATE_TIME.withHour(4)),
            new IdWithDataTime(5, DATE_TIME.withHour(5)),
            new IdWithDataTime(6, DATE_TIME.withHour(6)),
            new IdWithDataTime(7, DATE_TIME.withHour(7)),
            new IdWithDataTime(8, DATE_TIME.withHour(8)),
            new IdWithDataTime(9, DATE_TIME.withHour(9)),
            new IdWithDataTime(10, DATE_TIME.withHour(10)),
            new IdWithDataTime(11, DATE_TIME.withHour(11)),
            new IdWithDataTime(12, DATE_TIME.withHour(12)),
            new IdWithDataTime(13, DATE_TIME.withHour(13)),
            new IdWithDataTime(14, DATE_TIME.withHour(14)),
            new IdWithDataTime(15, DATE_TIME.withHour(15)),
            new IdWithDataTime(16, DATE_TIME.withHour(16)),
            new IdWithDataTime(17, DATE_TIME.withHour(17)),
            new IdWithDataTime(18, DATE_TIME.withHour(18)),
            new IdWithDataTime(19, DATE_TIME.withHour(19)),
            new IdWithDataTime(20, DATE_TIME.withHour(20)),
            new IdWithDataTime(21, DATE_TIME.withHour(21)),
            new IdWithDataTime(22, DATE_TIME.withHour(22)),
            new IdWithDataTime(23, DATE_TIME.withHour(23))
    };

    @Test
    public void collectForWeekRowView_shouldReturnCorrectCollection_whenCalled() {
        final List<IdWithDataTime> idsWithDataTimes = DateTimesCollector.collectForWeekRowView
                (DATE_TIME);
        Assert.assertThat(idsWithDataTimes, CoreMatchers.hasItems(EXPECTED_IDS_WITH_DATE_TIMES));
    }

}
