package com.example.radle.todo_calendar2.calendarView.tools;

import android.graphics.Rect;

import androidx.annotation.NonNull;

import com.example.radle.todo_calendar2.calendarView.CalendarRowView;
import com.example.radle.todo_calendar2.calendarView.RowParams;
import com.example.radle.todo_calendar2.calendarView.TopLabelRow;
import com.example.radle.todo_calendar2.calendarView.dto.CalendarField;
import com.example.radle.todo_calendar2.calendarView.dto.CalendarLabel;
import com.example.radle.todo_calendar2.calendarView.dto.IdWithDataTime;

import java.time.LocalDateTime;

public class CalendarRowElementsComposer {


    private static final int ADDITIONAL_LABEL_COLUMN = 1;
    private static final double TEXT_X_PROPORTIONS = 0.2;
    private static final double TEXT_Y_PROPORTIONS = 0.3;
    private static final float TEXT_HEIGHT_PROPORTIONS = 0.25F;

    public CalendarField getCalendarField(final CalendarRowView.RowParams rowParams, final
    int columnId, final LocalDateTime localDateTime) {
        final int fieldWidth = fieldWidth(rowParams);
        @SuppressWarnings("UnnecessaryLocalVariable") final int labelWidth = fieldWidth;
        final Rect rect = new Rect();
        rect.top = 0;
        rect.left = labelWidth + columnId * fieldWidth;
        rect.right = rect.left + fieldWidth;
        rect.bottom = rowParams.getHeight();
        return new CalendarField(columnId, rect, localDateTime);
    }

    public CalendarLabel getRowLabel(final CalendarRowView.RowParams rowParams) throws
            HourTextFormatter.NotRealHourNumberException {

        return new CalendarLabel(generateRowLabelText(rowParams), getTextX(rowParams),
                getTextY(rowParams), fieldWidth(rowParams));
    }

    public CalendarLabel getTopLabel(final TopLabelRow.RowParams rowParams,
                                     final IdWithDataTime idWithDataTime) {
        return new CalendarLabel(
                new DayTextFormatter().format(idWithDataTime.getDateTime().toLocalDate()),
                getColumnX(rowParams, idWithDataTime.getId()),
                getTextY(rowParams), fieldWidth(rowParams), idWithDataTime.isToday());
    }

    private int getColumnX(final TopLabelRow.RowParams rowParams, final int id) {
        return (id + 1) * fieldWidth(rowParams);
    }

    @NonNull
    private String generateRowLabelText(final CalendarRowView.RowParams rowParams) throws
            HourTextFormatter.NotRealHourNumberException {
        return new HourTextFormatter().format(rowParams.getId());
    }

    private int getTextX(final RowParams rowParams) {
        return (int) (fieldWidth(rowParams) * TEXT_X_PROPORTIONS);
    }

    private int getTextY(final RowParams rowParams) {
        return (int) (rowParams.getHeight() * TEXT_Y_PROPORTIONS);
    }

    private int fieldWidth(final RowParams rowParams) {
        return rowParams.getWidth() / (rowParams.getNumberOfColumns() + ADDITIONAL_LABEL_COLUMN);
    }

    public float getTextSize(final RowParams params) {
        return params.getHeight() * TEXT_HEIGHT_PROPORTIONS;
    }

}
