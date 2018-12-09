package com.example.radle.todo_calendar2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class CalendarRowView extends View {
    private RowParams rowParams;
    private CalendarRowLabel label;
    private List<CalendarField> calendarFields;
    private Paint labelTextPaint;

    public CalendarRowView(final Context context) {
        super(context);
    }

    public void compose() {
        this.label = getCalendarLabel();
        this.calendarFields = DateTimesCollector
                .collectForWeekRowView(this.rowParams.getFirstDateTime())
                .stream()
                .map(this::getCalendarField).collect(toList());
    }

    private CalendarRowLabel getCalendarLabel() {
        return CalendarRowElementsComposer.getLabel(this.rowParams);
    }

    private CalendarField getCalendarField(final IdWithDataTime idWithDataTime) {
        return CalendarRowElementsComposer.getCalendarField(this.rowParams, idWithDataTime.getId(),
                idWithDataTime.getDateTime());
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        canvas.drawText(this.label.getText(), this.label.getTextX(), this.label.getTextY(), this
                .labelTextPaint);
    }
}
