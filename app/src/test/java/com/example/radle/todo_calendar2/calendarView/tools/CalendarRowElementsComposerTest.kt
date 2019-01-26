package com.example.radle.todo_calendar2.calendarView.tools

import android.graphics.Rect
import com.example.radle.todo_calendar2.calendarView.CalendarRowView
import com.example.radle.todo_calendar2.calendarView.dto.CalendarField
import com.example.radle.todo_calendar2.calendarView.dto.CalendarRowLabel
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime
import java.time.LocalTime

class CalendarRowElementsComposerTest {

    @Test
    fun getCalendarField_shouldReturnZeroSizeField_whenWidthAndHeightAreZeros() {
        assertEquals(
                expectedCalednarField(0, 0, 0, 0, 0, DATE_TIME),
                CalendarRowElementsComposer.getCalendarField(
                        CalendarRowView.RowParams(0, 0, NUMBER_OF_COLUMNS, 0, DATE_TIME), 0, DATE_TIME))
    }

    @Test
    fun getCalendarField_shouldReturnFirstField_ifZeroIdIsPAssed() {
        assertEquals(
                expectedCalednarField(0, 10, 20, 10, 0, DATE_TIME),
                CalendarRowElementsComposer.getCalendarField(
                        CalendarRowView.RowParams(WIDTH, HEIGHT, NUMBER_OF_COLUMNS, 0, DATE_TIME), 0,
                        DATE_TIME))
    }

    @Test
    fun getCalendarField_shouldReturnThirdField_ifIdTwoIsPassed() {
        assertEquals(
                expectedCalednarField(0, 30, 40, 10, 2, DATE_TIME),
                CalendarRowElementsComposer.getCalendarField(
                        CalendarRowView.RowParams(WIDTH, HEIGHT, NUMBER_OF_COLUMNS, 0, DATE_TIME), 2,
                        DATE_TIME))
    }

    @Test
    fun getFirstLabel_shouldReturnCorrectLabel_whenIsCalled() {
        assertEquals(
                expectedLabel(0, 2, 5),
                CalendarRowElementsComposer.getLabel(CalendarRowView.RowParams(WIDTH, HEIGHT,
                        NUMBER_OF_COLUMNS, 0, DATE_TIME))
        )
    }

    @Test
    fun getSomeLabel_shouldReturnCorrectLabel_whenIsCalled() {
        assertEquals(
                expectedLabel(13, 4, 10),
                CalendarRowElementsComposer.getLabel(CalendarRowView.RowParams(WIDTH * 2, HEIGHT * 2, 7, 13,
                        DATE_TIME))
        )
    }

    private fun expectedLabel(hourOfDay: Int, textX: Int, textY: Int): CalendarRowLabel {
        return CalendarRowLabel(HourTextFormatter.format(hourOfDay), textX, textY, LocalTime.of(hourOfDay, 0))
    }

    private fun expectedCalednarField(
            top: Int, left: Int, right: Int, bottom: Int, id: Int, dateTime: LocalDateTime): CalendarField {
        val rect = Rect()
        rect.top = top
        rect.left = left
        rect.right = right
        rect.bottom = bottom
        return CalendarField(id, rect, dateTime)
    }

    companion object {

        private const val NUMBER_OF_COLUMNS = 7
        private const val WIDTH = 80
        private const val HEIGHT = 10
        private val DATE_TIME = LocalDateTime.now()
    }
}
