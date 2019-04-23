package com.example.radle.todo_calendar2.calendarView.tools;

import com.example.radle.todo_calendar2.calendarView.BoardListView;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertSame;

public class CalendarMeasureTest {

    private final CalendarMeasure subject = new CalendarMeasure();
    private final LocalDateTime DATE_TIME = LocalDateTime.now();

    @Test
    public void measureRowHeight_shouldReturnZero_whenBoardWidthIsZero() {
        assertSame(0, this.subject.measureRowHeight(new BoardListView.BoardParams(1,
                this.DATE_TIME)));
    }

    @Test
    public void mesureRowHeight_shouldReturnWidth_whenNumberOfColumnsIsZero() {
        assertSame(10, this.subject.measureRowHeight(new BoardListView.BoardParams(10, 2, 0,
                this.DATE_TIME)));
    }

    @Test
    public void measureRowHeight_shouldReturnWidthByNumberOfColsPlusOne_whenNumberOfColumnsIsGreaterThanZero() {
        assertSame(1, this.subject.measureRowHeight(new BoardListView.BoardParams(8, 2, 7,
                this.DATE_TIME)));
    }

}