package com.example.radle.todo_calendar2.calendarView.tools

import com.example.radle.todo_calendar2.calendarView.dto.IdWithDataTime

import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Test

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


class DateTimesCollectorTest {

    @Test
    fun collectForWeekRowView_shouldReturnCorrectCollection_whenCalled() {
        val idsWithDataTimes = DateTimesCollector.collectForWeekRowView(DATE_TIME)
        Assert.assertThat(idsWithDataTimes, CoreMatchers.hasItems(*EXPECTED_IDS_WITH_DATE_TIMES))
    }

    companion object {
        private val DATE_TIME = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0))
        private val EXPECTED_IDS_WITH_DATE_TIMES = arrayOf(
                IdWithDataTime(0, DATE_TIME.withHour(0)),
                IdWithDataTime(1, DATE_TIME.withHour(1)),
                IdWithDataTime(2, DATE_TIME.withHour(2)),
                IdWithDataTime(3, DATE_TIME.withHour(3)),
                IdWithDataTime(4, DATE_TIME.withHour(4)),
                IdWithDataTime(5, DATE_TIME.withHour(5)),
                IdWithDataTime(6, DATE_TIME.withHour(6)),
                IdWithDataTime(7, DATE_TIME.withHour(7)),
                IdWithDataTime(8, DATE_TIME.withHour(8)),
                IdWithDataTime(9, DATE_TIME.withHour(9)),
                IdWithDataTime(10, DATE_TIME.withHour(10)),
                IdWithDataTime(11, DATE_TIME.withHour(11)),
                IdWithDataTime(12, DATE_TIME.withHour(12)),
                IdWithDataTime(13, DATE_TIME.withHour(13)),
                IdWithDataTime(14, DATE_TIME.withHour(14)),
                IdWithDataTime(15, DATE_TIME.withHour(15)),
                IdWithDataTime(16, DATE_TIME.withHour(16)),
                IdWithDataTime(17, DATE_TIME.withHour(17)),
                IdWithDataTime(18, DATE_TIME.withHour(18)),
                IdWithDataTime(19, DATE_TIME.withHour(19)),
                IdWithDataTime(20, DATE_TIME.withHour(20)),
                IdWithDataTime(21, DATE_TIME.withHour(21)),
                IdWithDataTime(22, DATE_TIME.withHour(22)),
                IdWithDataTime(23, DATE_TIME.withHour(23)))
    }

}
