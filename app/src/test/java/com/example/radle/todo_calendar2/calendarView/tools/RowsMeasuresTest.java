package com.example.radle.todo_calendar2.calendarView.tools;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertSame;

public class RowsMeasuresTest {

    private final RowsMeasures subject = new RowsMeasures();
    private final LocalDateTime DATE_TIME = LocalDateTime.now();

    @Test
    public void measureRowHeight_shouldReturnZero_whenBoardSizeIsZero() {
        assertSame(0, this.subject.measureRowHeight(0, 1));
    }

    @Test
    public void mesureRowHeight_shouldReturnWidth_whenNumberOfColumnsIsZero() {
        assertSame(10, this.subject.measureRowHeight(10, 0));
    }

    @Test
    public void
    measureRowHeight_shouldReturnWidthByNumberOfColsPlusOne_whenNumberOfColumnsIsGreaterThanZero() {
        assertSame(1, this.subject.measureRowHeight(8, 7));
    }

}