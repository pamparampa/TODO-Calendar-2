package com.example.radle.todo_calendar2.utils;

import com.example.radle.todo_calendar2.todoList.entity.Period;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class AvailablePeriodsUtils {
    public static List<Period> getAvailablePeriods(LocalDate date) {
        if (date.isBefore(getStartOfSpring(date))) {
            return Arrays.asList(Period.TODAY, Period.TOMORROW, Period.THIS_WEEK, Period.NEXT_WEEK,
                    Period.THIS_MONTH, Period.NEXT_MONTH, Period.THIS_WINTER, Period.THIS_SPRING,
                    Period.THIS_SUMMER, Period.THIS_AUTUMN, Period.NEXT_YEAR, Period.SOMETIME);

        } else if (date.isBefore(getStartOfSummer(date))) {
            return Arrays.asList(Period.TODAY, Period.TOMORROW, Period.THIS_WEEK, Period.NEXT_WEEK,
                    Period.THIS_MONTH, Period.NEXT_MONTH, Period.THIS_SPRING, Period.THIS_SUMMER,
                    Period.THIS_AUTUMN, Period.THIS_WINTER2, Period.NEXT_YEAR, Period.SOMETIME);

        } else if (date.isBefore(getStartOfAutumn(date))) {
            return Arrays.asList(Period.TODAY, Period.TOMORROW, Period.THIS_WEEK, Period.NEXT_WEEK,
                    Period.THIS_MONTH, Period.NEXT_MONTH, Period.THIS_SUMMER, Period.THIS_AUTUMN,
                    Period.THIS_WINTER2, Period.NEXT_YEAR, Period.NEXT_SPRING, Period.SOMETIME);

        } else if (date.isBefore(getStartOfWinter(date))) {
            return Arrays.asList(Period.TODAY, Period.TOMORROW, Period.THIS_WEEK, Period.NEXT_WEEK,
                    Period.THIS_MONTH, Period.NEXT_MONTH, Period.THIS_AUTUMN, Period.THIS_WINTER2,
                    Period.NEXT_YEAR, Period.NEXT_SPRING, Period.NEXT_SUMMER, Period.SOMETIME);

        } else {
            return Arrays.asList(Period.TODAY, Period.TOMORROW, Period.THIS_WEEK, Period.NEXT_WEEK,
                    Period.THIS_MONTH, Period.NEXT_MONTH, Period.THIS_WINTER2, Period.NEXT_YEAR,
                    Period.NEXT_SPRING, Period.NEXT_SUMMER, Period.NEXT_AUTUMN, Period.SOMETIME);
        }
    }

    private static LocalDate getStartOfSpring(LocalDate date) {
        return LocalDate.of(date.getYear(), Month.MARCH, 20);
    }

    private static LocalDate getStartOfSummer(LocalDate date) {
        return LocalDate.of(date.getYear(), Month.JUNE, 21);
    }

    private static LocalDate getStartOfAutumn(LocalDate date) {
        return LocalDate.of(date.getYear(), Month.SEPTEMBER, 22);
    }

    private static LocalDate getStartOfWinter(LocalDate date) {
        return LocalDate.of(date.getYear(), Month.DECEMBER, 21);
    }
}
