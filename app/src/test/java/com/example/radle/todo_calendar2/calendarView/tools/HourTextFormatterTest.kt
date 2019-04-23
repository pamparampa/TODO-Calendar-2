package com.example.radle.todo_calendar2.calendarView.tools

import org.junit.Assert.assertEquals
import org.junit.Test

class HourTextFormatterTest {

    @Test
    @Throws(HourTextFormatter.NotRealHourNumberException::class)
    fun format_shouldAddZeroZero_whenTwoDigitsRealHourIsPassed() {
        assertEquals("23:00", hourTextFormatter.format(23))
    }

    @Test
    @Throws(HourTextFormatter.NotRealHourNumberException::class)
    fun format_shouldAddPrefixZeroAndZeroZero_whenOneDigitHourIsPassed() {
        assertEquals("00:00", hourTextFormatter.format(0))
    }

    @Test(expected = HourTextFormatter.NotRealHourNumberException::class)
    @Throws(HourTextFormatter.NotRealHourNumberException::class)
    fun format_shouldThrowNotRealHourNumberException_whenNumberHigherThan23IsPassed() {
        hourTextFormatter.format(24)
    }

    @Test(expected = HourTextFormatter.NotRealHourNumberException::class)
    @Throws(HourTextFormatter.NotRealHourNumberException::class)
    fun format_shouldThrowNotRealHourNumberException_whenNumberLowerThanZero() {
        hourTextFormatter.format(-1)

    }

    companion object {
        private val hourTextFormatter = HourTextFormatter()
    }
}