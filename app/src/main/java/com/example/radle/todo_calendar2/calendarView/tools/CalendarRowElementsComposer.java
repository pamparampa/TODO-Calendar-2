package com.example.radle.todo_calendar2.calendarView.tools;

import android.graphics.Rect;

import com.example.radle.todo_calendar2.calendarView.CalendarRowView;
import com.example.radle.todo_calendar2.calendarView.dto.CalendarField;
import com.example.radle.todo_calendar2.calendarView.dto.CalendarRowLabel;

import java.time.LocalDateTime;
import java.time.LocalTime;

import androidx.annotation.NonNull;

public final class CalendarRowElementsComposer {


    private static final int ADDITIONAL_LABEL_COLUMN = 1;
    private static final double TEXT_X_PROPORTIONS = 0.2;
    private static final double TEXT_Y_PROPORTIONS = 0.5;
    private static final float TEXT_HEIGHT_PROPORTIONS = 0.4F;

    public static CalendarField getCalendarField(final CalendarRowView.RowParams rowParams, final
    int columnId, final LocalDateTime localDateTime) {
        final int fieldWidth = fieldWidth(rowParams);
        final int labelWidth = fieldWidth;
        final Rect rect = new Rect();
        rect.top = 0;
        rect.left = labelWidth + columnId * fieldWidth;
        rect.right = rect.left + fieldWidth;
        rect.bottom = rowParams.getHeight();
        return new CalendarField(columnId, rect, localDateTime);
    }

    private static int fieldWidth(final CalendarRowView.RowParams rowParams) {
        return rowParams.getWidth() / (rowParams.getNumberOfColumns() + ADDITIONAL_LABEL_COLUMN);
    }

    public static CalendarRowLabel getLabel(final CalendarRowView.RowParams rowParams) throws
            HourTextFormatter.NotRealHourNumberException {

        return new CalendarRowLabel(generateLabelText(rowParams), getTextX(rowParams),
                getTextY(rowParams), generateLocalTime(rowParams));
    }

    @NonNull
    private static String generateLabelText(final CalendarRowView.RowParams rowParams) throws
            HourTextFormatter.NotRealHourNumberException {
        return HourTextFormatter.format(rowParams.getId());
    }

    private static int getTextX(final CalendarRowView.RowParams rowParams) {
        return (int) (fieldWidth(rowParams) * TEXT_X_PROPORTIONS);
    }

    private static int getTextY(final CalendarRowView.RowParams rowParams) {
        return (int) (rowParams.getHeight() * TEXT_Y_PROPORTIONS);
    }

    private static LocalTime generateLocalTime(final CalendarRowView.RowParams rowParams) {
        return LocalTime.of(rowParams.getId(), 0);
    }

    public static float getTextSize(final CalendarRowView.RowParams params) {
        return params.getHeight() * TEXT_HEIGHT_PROPORTIONS;
    }
}
