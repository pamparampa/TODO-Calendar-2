package com.example.radle.todo_calendar2.calendarView.tools

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate
import java.time.Month

class DayTextFormatterTest {

    @Test // Works only if system default locale is poland
    fun format_shouldReturnFirstLetterFromDayOfWeekAndNumberOfDayBelow_whenIsCalled() {
        assertEquals("Ś\n1", dayTextFormatter.format(LocalDate.of(2019, Month.MAY, 1)))
    }

    companion object {
        private val dayTextFormatter = DayTextFormatter()

    }
}