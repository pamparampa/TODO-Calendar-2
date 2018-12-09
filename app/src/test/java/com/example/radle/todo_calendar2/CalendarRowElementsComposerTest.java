package com.example.radle.todo_calendar2;

import android.graphics.Rect;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

public class CalendarRowElementsComposerTest {

    private static final int NUMBER_OF_COLUMNS = 7;
    private static final int WIDTH = 80;
    private static final int HEIGHT = 10;
    private static final LocalDateTime DATE_TIME = LocalDateTime.now();

    @Test
    public void getCalendarField_shouldReturnZeroSizeField_whenWidthAndHeightAreZeros() {
        assertEquals(
                expectedCalednarField(0, 0, 0, 0, 0, DATE_TIME),
                CalendarRowElementsComposer.getCalendarField(
                        new RowParams(0, 0, NUMBER_OF_COLUMNS, 0, DATE_TIME), 0, DATE_TIME));
    }

    @Test
    public void getCalendarField_shouldReturnFirstField_ifZeroIdIsPAssed() {
        assertEquals(
                expectedCalednarField(0, 10, 20, 10, 0, DATE_TIME),
                CalendarRowElementsComposer.getCalendarField(
                        new RowParams(WIDTH, HEIGHT, NUMBER_OF_COLUMNS, 0, DATE_TIME), 0,
                        DATE_TIME));
    }

    @Test
    public void getCalendarField_shouldReturnThirdField_ifIdTwoIsPassed() {
        assertEquals(
                expectedCalednarField(0, 30, 40, 10, 2, DATE_TIME),
                CalendarRowElementsComposer.getCalendarField(
                        new RowParams(WIDTH, HEIGHT, NUMBER_OF_COLUMNS, 0, DATE_TIME), 2,
                        DATE_TIME));
    }

    @Test
    public void getFirstLabel_shouldReturnCorrectLabel_whenIsCalled() {
        assertEquals(
                expectedLabel(0, 1, 1),
                CalendarRowElementsComposer.getLabel(new RowParams(WIDTH, HEIGHT,
                        NUMBER_OF_COLUMNS, 0, DATE_TIME))
        );
    }

    @Test
    public void getSomeLabel_shouldReturnCorrectLabel_whenIsCalled() {
        assertEquals(
                expectedLabel(13, 2, 2),
                CalendarRowElementsComposer.getLabel(new RowParams(WIDTH * 2, HEIGHT * 2, 7, 13,
                        DATE_TIME))
        );
    }

    private CalendarRowLabel expectedLabel(final int hourOfDay, final int textX, final int textY) {
        return new CalendarRowLabel(hourOfDay + ":00", textX, textY, LocalTime.of(hourOfDay, 0));
    }

    private CalendarField expectedCalednarField(
            final int top, final int left, final int right, final int bottom, final int id, final
    LocalDateTime dateTime) {
        final Rect rect = new Rect();
        rect.top = top;
        rect.left = left;
        rect.right = right;
        rect.bottom = bottom;
        return new CalendarField(id, rect, dateTime);
    }
}
