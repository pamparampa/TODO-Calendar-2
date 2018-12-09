package com.example.radle.todo_calendar2.calendarView.tools;

import android.graphics.Rect;
import android.support.annotation.NonNull;

import com.example.radle.todo_calendar2.calendarView.dto.CalendarField;
import com.example.radle.todo_calendar2.calendarView.dto.CalendarRowLabel;
import com.example.radle.todo_calendar2.calendarView.params.RowParams;

import java.time.LocalDateTime;
import java.time.LocalTime;

public final class CalendarRowElementsComposer {


    private static final int ADDITIONAL_LABEL_COLUMN = 1;
    private static final int TEXT_X_PROPORTIONS = 10;
    private static final int TEXT_Y_PROPORTIONS = 10;

    public static CalendarField getCalendarField(final RowParams rowParams, final int rowId, final
    LocalDateTime localDateTime) {
        final int fieldWidth = fieldWidth(rowParams);
        final int labelWidth = fieldWidth;
        final Rect rect = new Rect();
        rect.top = 0;
        rect.left = labelWidth + rowId * fieldWidth;
        rect.right = rect.left + fieldWidth;
        rect.bottom = rowParams.getHeight();
        return new CalendarField(rowId, rect, localDateTime);
    }

    private static int fieldWidth(final RowParams rowParams) {
        return rowParams.getWidth() / (rowParams.getNumberOfColumns() + ADDITIONAL_LABEL_COLUMN);
    }

    public static CalendarRowLabel getLabel(final RowParams rowParams) {

        return new CalendarRowLabel(generateLabelText(rowParams), getTextX(rowParams),
                getTextY(rowParams), generateLocalTime(rowParams));
    }

    @NonNull
    private static String generateLabelText(final RowParams rowParams) {
        return rowParams.getId() + ":00";
    }

    private static int getTextX(final RowParams rowParams) {
        return fieldWidth(rowParams) / TEXT_X_PROPORTIONS;
    }

    private static int getTextY(final RowParams rowParams) {
        return rowParams.getHeight() / TEXT_Y_PROPORTIONS;
    }

    private static LocalTime generateLocalTime(final RowParams rowParams) {
        return LocalTime.of(rowParams.getId(), 0);
    }
}
