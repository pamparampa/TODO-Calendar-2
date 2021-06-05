package com.example.radle.todo_calendar2.calendarView.tools;

import com.example.radle.todo_calendar2.calendarView.BoardScrollView;
import com.example.radle.todo_calendar2.dto.CalendarSelection;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;

public class CalendarPositionsHandlerTest {
    private final CalendarPositionsHandler handler = new CalendarPositionsHandler(new BoardScrollView.BoardParams(
            80, 10, 7,
            LocalDateTime.of(2021, Month.MAY, 10, 0, 0)));
    @Test
    public void determineSelection_shouldReturnNull_whenZeroZeroPositionIsSelected() {
        Assertions.assertThat(handler.determineSelection(0, 0)).isEqualTo(null);
    }

    @Test
    public void determineSelection_shouldReturnValidRoundedSelection_whenSomeInaccurateTimeSelected() {
        CalendarSelection expectedSelection = new CalendarSelection.Builder()
                .withX(10)
                .withSelectedTime(LocalDateTime.of(2021, Month.MAY, 10, 1, 42))
                .withLeft(10).withTop(15).withRight(20).withBottom(20)
                .withStartTime(LocalDateTime.of(2021, Month.MAY, 10, 1, 30))
                .withEndTime(LocalDateTime.of(2021, Month.MAY, 10, 2, 0))
                .build();
        Assertions.assertThat(handler.determineSelection(10, 17)).isEqualTo(expectedSelection);
    }

    @Test
    public void determineSelection_shouldReturnValidSelection_whenPositionForSomeDayAndSomeTimeSelected() {
        CalendarSelection expectedSelection = new CalendarSelection.Builder()
                .withX(68)
                .withSelectedTime(LocalDateTime.of(2021, Month.MAY, 15, 2, 0))
                .withLeft(60).withTop(20).withRight(70).withBottom(25)
                .withStartTime(LocalDateTime.of(2021, Month.MAY, 15, 2, 0))
                .withEndTime(LocalDateTime.of(2021, Month.MAY, 15, 2, 30))
                .build();
        Assertions.assertThat(handler.determineSelection(68, 20)).isEqualTo(expectedSelection);
    }
}