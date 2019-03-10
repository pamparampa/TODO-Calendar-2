package com.example.radle.todo_calendar2.calendarView.tools

import org.junit.Test

import org.junit.Assert.assertEquals

class HourTextFormatterTest {

    @Test
    @Throws(HourTextFormatter.NotRealHourNumberException::class)
    fun format_shouldAddZeroZero_whenTwoDigitsRealHourIsPassed() {
        assertEquals("23:00", HourTextFormatter.format(23))
    }

    @Test
    @Throws(HourTextFormatter.NotRealHourNumberException::class)
    fun format_shouldAddPrefixZeroAndZeroZero_whenOneDigitHourIsPassed() {
        assertEquals("00:00", HourTextFormatter.format(0))
    }

    @Test(expected = HourTextFormatter.NotRealHourNumberException::class)
    @Throws(HourTextFormatter.NotRealHourNumberException::class)
    fun format_shouldThrowNotRealHourNumberException_whenNumberHigherThan23IsPassed() {
        HourTextFormatter.format(24)
    }

    @Test(expected = HourTextFormatter.NotRealHourNumberException::class)
    @Throws(HourTextFormatter.NotRealHourNumberException::class)
    fun format_shouldThrowNotRealHourNumberException_whenNumberLowerThanZero() {
        HourTextFormatter.format(-1)

    }

}