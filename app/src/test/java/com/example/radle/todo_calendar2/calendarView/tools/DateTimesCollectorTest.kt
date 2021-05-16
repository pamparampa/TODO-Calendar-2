package com.example.radle.todo_calendar2.calendarView.tools

import com.example.radle.todo_calendar2.calendarView.TimeNotAlignedException
import com.example.radle.todo_calendar2.dto.IdWithDataTime
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Test
import java.time.LocalDate
import java.time.Month


class DateTimesCollectorTest {

    @Test
    fun collectForWeekRow_shouldReturnCorrectCollection_whenCorrectDateTimeIsPassed() {
        val idsWithDataTimes = dateTimesCollector.collectForWeekRow(DATE_TIME)
        Assert.assertThat(idsWithDataTimes, CoreMatchers.hasItems(*EXPECTED_FOR_WEEK_ROW))
    }

    @Test(expected = TimeNotAlignedException::class)
    fun collectForWeekRow_shouldThrowTimeNotAlignedException_whenNotCorrectDateTimeIsPassed() {
        dateTimesCollector.collectForWeekRow(DATE_TIME.withMinute(1))
    }

    @Test
    fun collectForWeekColumn_shouldReturnCorrectCollection_whenCorrectDateTimeIsPassed() {
        val idsWithDateTimes = dateTimesCollector.collectRowsForWeek(DATE_TIME)
        Assert.assertThat(idsWithDateTimes, CoreMatchers.hasItems(*EXPECTED_FOR_WEEK_COLUMN))
    }

    @Test(expected = TimeNotAlignedException::class)
    fun collectForWeekColumn_shouldThrowTimeNotAlignedException_whenNotCorrectDateTimeIsPassed() {
        dateTimesCollector.collectRowsForWeek(DATE_TIME.withMinute(1))
    }

    @Test
    fun collectForTopLabelRow_shouldReturnCorrectCollection_whenCorrectDateTimeIsPassed() {
        val idsWithDataTimes = dateTimesCollector.collectForTopLabelRow(DATE_TIME)
        Assert.assertThat(idsWithDataTimes, CoreMatchers.hasItems(*EXPECTED_FOR_WEEK_ROW))
    }

    @Test(expected = TimeNotAlignedException::class)
    fun collectForTopLabelRow_shouldThrowTimeNotAlignedException_whenNotCorrectDateTimeIsPassed() {
        dateTimesCollector.collectForTopLabelRow(DATE_TIME.plusDays(1))
    }

    companion object {

        private val DATE_TIME = LocalDate.of(2019, Month.FEBRUARY, 25).atStartOfDay()
        private val EXPECTED_FOR_WEEK_ROW = arrayOf(
                IdWithDataTime(0, DATE_TIME),
                IdWithDataTime(1, DATE_TIME.plusDays(1)),
                IdWithDataTime(2, DATE_TIME.plusDays(2)),
                IdWithDataTime(3, DATE_TIME.plusDays(3)),
                IdWithDataTime(4, DATE_TIME.plusDays(4)),
                IdWithDataTime(5, DATE_TIME.plusDays(5)),
                IdWithDataTime(6, DATE_TIME.plusDays(6)))
        private val EXPECTED_FOR_WEEK_COLUMN = arrayOf(
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

        private val dateTimesCollector = DateTimesCollector()
    }

}
