package com.example.radle.todo_calendar2.calendarView.tools;

public class HourTextFormatter {

    public String format(final int hour) throws NotRealHourNumberException {
        if (hour > 23 || hour < 0) throw new NotRealHourNumberException(hour);
        return String.format("%02d", hour) + ":00";
    }

    public static class NotRealHourNumberException extends Exception {
        private final int hour;

        NotRealHourNumberException(final int hour) {
            this.hour = hour;
        }

        @Override
        public String getMessage() {
            return "not valid hour passed: " + this.hour;
        }
    }
}
