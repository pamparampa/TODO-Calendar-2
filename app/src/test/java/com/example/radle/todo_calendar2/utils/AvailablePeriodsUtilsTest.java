package com.example.radle.todo_calendar2.utils;

import com.example.radle.todo_calendar2.todoList.entity.Period;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;

public class AvailablePeriodsUtilsTest {

    @Test
    public void getAvailablePeriods_shouldReturnCorrectPeriods_ForWinterOfYearBeginning() {
        Assertions.assertThat(
                AvailablePeriodsUtils.getAvailablePeriods(LocalDate.of(2022, Month.FEBRUARY, 1)))
                .containsExactly(
                        Period.TODAY, Period.TOMORROW, Period.THIS_WEEK, Period.NEXT_WEEK,
                        Period.THIS_MONTH, Period.NEXT_MONTH, Period.THIS_WINTER, Period.THIS_SPRING,
                        Period.THIS_SUMMER, Period.THIS_AUTUMN, Period.NEXT_WINTER, Period.NEXT_YEAR, Period.SOMETIME);
    }

    @Test
    public void getAvailablePeriods_shouldReturnCorrectPeriods_ForSpring() {
        Assertions.assertThat(
                AvailablePeriodsUtils.getAvailablePeriods(LocalDate.of(2022, Month.MARCH, 30)))
                .containsExactly(
                        Period.TODAY, Period.TOMORROW, Period.THIS_WEEK, Period.NEXT_WEEK,
                        Period.THIS_MONTH, Period.NEXT_MONTH, Period.THIS_SPRING, Period.THIS_SUMMER,
                        Period.THIS_AUTUMN, Period.THIS_WINTER2, Period.THIS_YEAR, Period.NEXT_SPRING,
                        Period.NEXT_YEAR, Period.SOMETIME);
    }

    @Test
    public void getAvailablePeriods_shouldReturnCorrectPeriods_ForSummer() {
        Assertions.assertThat(
                AvailablePeriodsUtils.getAvailablePeriods(LocalDate.of(2022, Month.AUGUST, 20)))
                .containsExactly(
                        Period.TODAY, Period.TOMORROW, Period.THIS_WEEK, Period.NEXT_WEEK,
                        Period.THIS_MONTH, Period.NEXT_MONTH, Period.THIS_SUMMER, Period.THIS_AUTUMN,
                        Period.THIS_WINTER2, Period.THIS_YEAR, Period.NEXT_SPRING, Period.NEXT_SUMMER,
                        Period.NEXT_YEAR, Period.SOMETIME);
    }

    @Test
    public void getAvailablePeriods_shouldReturnCorrectPeriods_ForAutumn() {
        Assertions.assertThat(
                AvailablePeriodsUtils.getAvailablePeriods(LocalDate.of(2022, Month.NOVEMBER, 2)))
                .containsExactly(
                        Period.TODAY, Period.TOMORROW, Period.THIS_WEEK, Period.NEXT_WEEK,
                        Period.THIS_MONTH, Period.NEXT_MONTH, Period.THIS_AUTUMN, Period.THIS_WINTER2,
                        Period.THIS_YEAR, Period.NEXT_SPRING, Period.NEXT_SUMMER, Period.NEXT_AUTUMN,
                        Period.NEXT_YEAR, Period.SOMETIME);
    }

    @Test
    public void getAvailablePeriods_shouldReturnCorrectPeriods_ForWinterOfYearEnding() {
        Assertions.assertThat(
                AvailablePeriodsUtils.getAvailablePeriods(LocalDate.of(2022, Month.DECEMBER, 31)))
                .containsExactly(
                        Period.TODAY, Period.TOMORROW, Period.THIS_WEEK, Period.NEXT_WEEK,
                        Period.THIS_MONTH, Period.NEXT_MONTH, Period.THIS_WINTER2, Period.NEXT_YEAR,
                        Period.NEXT_SPRING, Period.NEXT_SUMMER, Period.NEXT_AUTUMN, Period.SOMETIME);
    }
}