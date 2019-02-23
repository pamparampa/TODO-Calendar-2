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
                IdWithDataTime(0, DATE_TIME),
                IdWithDataTime(1, DATE_TIME.plusDays(1)),
                IdWithDataTime(2, DATE_TIME.plusDays(2)),
                IdWithDataTime(3, DATE_TIME.plusDays(3)),
                IdWithDataTime(4, DATE_TIME.plusDays(4)),
                IdWithDataTime(5, DATE_TIME.plusDays(5)),
                IdWithDataTime(6, DATE_TIME.plusDays(6)))
    }

}
