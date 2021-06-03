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
        CalendarSelection expectedSelection = new CalendarSelection(10, 15, 20, 20,
                LocalDateTime.of(2021, Month.MAY, 10, 1, 30),
                LocalDateTime.of(2021, Month.MAY, 10, 2, 0));
        Assertions.assertThat(handler.determineSelection(10, 17)).isEqualTo(expectedSelection);
    }

    @Test
    public void determineSelection_shouldReturnValidSelection_whenPositionForSomeDayAndSomeTimeSelected() {
        CalendarSelection expectedSelection = new CalendarSelection(60, 20, 70, 25,
                LocalDateTime.of(2021, Month.MAY, 15, 2, 0),
                LocalDateTime.of(2021, Month.MAY, 15, 2, 30));
        Assertions.assertThat(handler.determineSelection(68, 20)).isEqualTo(expectedSelection);
    }
}