package com.example.radle.todo_calendar2.calendarView.tools

import android.graphics.Rect
import com.example.radle.todo_calendar2.calendarView.CalendarRowView
import com.example.radle.todo_calendar2.calendarView.TopLabelRow
import com.example.radle.todo_calendar2.calendarView.dto.CalendarField
import com.example.radle.todo_calendar2.calendarView.dto.CalendarLabel
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime
import java.time.Month

class CalendarRowElementsComposerTest {

    @Test
    fun getCalendarField_shouldReturnZeroSizeField_whenWidthAndHeightAreZeros() {
        assertEquals(
                expectedCalednarField(0, 0, 0, 0, 0, DATE_TIME),
                composer.getCalendarField(
                        CalendarRowView.RowParams(0, 0, NUMBER_OF_COLUMNS, 0, DATE_TIME), 0, DATE_TIME))
    }

    @Test
    fun getCalendarField_shouldReturnFirstField_ifZeroIdIsPAssed() {
        assertEquals(
                expectedCalednarField(0, 10, 20, 10, 0, DATE_TIME),
                composer.getCalendarField(
                        CalendarRowView.RowParams(WIDTH, HEIGHT, NUMBER_OF_COLUMNS, 0, DATE_TIME), 0,
                        DATE_TIME))
    }

    @Test
    fun getCalendarField_shouldReturnThirdField_ifIdTwoIsPassed() {
        assertEquals(
                expectedCalednarField(0, 30, 40, 10, 2, DATE_TIME),
                composer.getCalendarField(
                        CalendarRowView.RowParams(WIDTH, HEIGHT, NUMBER_OF_COLUMNS, 0, DATE_TIME), 2,
                        DATE_TIME))
    }

    @Test
    fun getRowLabel_shouldReturnFirstLabel_whenZeroIdPassed() {
        assertEquals(
                expectedRowLabel(0, 2, 5),
                composer.getRowLabel(CalendarRowView.RowParams(WIDTH, HEIGHT,
                        NUMBER_OF_COLUMNS, 0, DATE_TIME))
        )
    }

    @Test
    fun getRowLabel_shouldReturCorrectLabel_whenNotZeroIdPassed() {
        assertEquals(
                expectedRowLabel(13, 4, 10),
                composer.getRowLabel(CalendarRowView.RowParams(WIDTH * 2, HEIGHT * 2, 7, 13,
                        DATE_TIME))
        )
    }

    @Test
    fun getTopLabel_shouldReturnFirstLabel_whenMondayDateIsPassed() {
        assertEquals(
                CalendarLabel("P\n29", 2, 5),
                composer.getTopLabel(TopLabelRow.RowParams(WIDTH, HEIGHT, NUMBER_OF_COLUMNS, DATE_TIME), DATE_TIME.toLocalDate())
        )
    }

    private fun expectedRowLabel(hourOfDay: Int, textX: Int, textY: Int): CalendarLabel {
        return CalendarLabel(HourTextFormatter().format(hourOfDay), textX, textY)
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
        private val DATE_TIME = LocalDateTime.of(2019, Month.APRIL, 29, 0, 0, 0);
        private val composer = CalendarRowElementsComposer()
    }
}
