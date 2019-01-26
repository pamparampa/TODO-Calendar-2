package com.example.radle.todo_calendar2.calendarView.tools;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HourTextFormatterTest {

    @Test
    public void format_shouldAddZeroZero_whenTwoDigitsRealHourIsPassed() throws HourTextFormatter
            .NotRealHourNumberException {
        assertEquals("23:00", HourTextFormatter.format(23));
    }

    @Test
    public void format_shouldAddPrefixZeroAndZeroZero_whenOneDigitHourIsPassed() throws
            HourTextFormatter.NotRealHourNumberException {
        assertEquals("00:00", HourTextFormatter.format((0)));
    }

    @Test(expected = HourTextFormatter.NotRealHourNumberException.class)
    public void format_shouldThrowNotRealHourNumberException_whenNumberHigherThan23IsPassed()
            throws HourTextFormatter.NotRealHourNumberException {
        HourTextFormatter.format(24);
    }

    @Test(expected = HourTextFormatter.NotRealHourNumberException.class)
    public void format_shouldThrowNotRealHourNumberException_whenNumberLowerThanZero() throws
            HourTextFormatter.NotRealHourNumberException {
        HourTextFormatter.format(-1);

    }

}