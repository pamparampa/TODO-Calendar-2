package com.example.radle.todo_calendar2.calendarView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.radle.todo_calendar2.calendarView.dto.CalendarField;
import com.example.radle.todo_calendar2.calendarView.dto.CalendarRowLabel;
import com.example.radle.todo_calendar2.calendarView.dto.IdWithDataTime;
import com.example.radle.todo_calendar2.calendarView.tools.CalendarRowElementsComposer;
import com.example.radle.todo_calendar2.calendarView.tools.DateTimesCollector;
import com.example.radle.todo_calendar2.calendarView.tools.HourTextFormatter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.stream.Collectors.toList;

public class CalendarRowView extends View {
    private RowParams params;
    private CalendarRowLabel label;
    private List<CalendarField> calendarFields;
    private final Paint labelTextPaint = new Paint();
    private final Paint rectPaint = new Paint();

    public CalendarRowView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        this.rectPaint.setStyle(Paint.Style.STROKE);
        this.rectPaint.setAntiAlias(true);
        this.labelTextPaint.setAntiAlias(true);
    }

    public void setParams(final RowParams params) {
        this.params = params;
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        setMeasuredDimension(resolveSize(this.params.getWidth(), widthMeasureSpec), resolveSize
                (this.params.getHeight(), heightMeasureSpec));
    }

    @Override
    protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
        this.params.height = h;
        this.params.width = w;
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        compose();      // TODO byc moze do innej metody, np. onSizeChanged
        canvas.drawText(this.label.getText(), this.label.getTextX(), this.label.getTextY(), this
                .labelTextPaint);
        this.calendarFields.forEach(calendarField -> canvas.drawRect(calendarField.getRect(),
                this.rectPaint));
    }

    private void compose() {
        prepareLabel();
        prepareCalendarFields();
    }

    private void prepareLabel() {
        try {
            this.label = CalendarRowElementsComposer.getLabel(this.params);
        } catch (final HourTextFormatter.NotRealHourNumberException e) {
            Logger.getLogger("Exception").log(Level.WARNING, "Not valid calendar element: ");
            e.printStackTrace();
        }
        this.labelTextPaint.setTextSize(CalendarRowElementsComposer.getTextSize(this.params));
    }

    private void prepareCalendarFields() {
        try {
            this.calendarFields = DateTimesCollector
                    .collectForWeekRowView(this.params.getRowFirsDateTime())
                    .stream()
                    .map(this::getCalendarField).collect(toList());
        } catch (final TimeNotAlignedException e) {
            e.printStackTrace();
        }
    }

    private CalendarField getCalendarField(final IdWithDataTime idWithDataTime) {
        return CalendarRowElementsComposer.getCalendarField(this.params, idWithDataTime.getId(),
                idWithDataTime.getDateTime());
    }

    public static class RowParams {
        private int width = 0;
        private int height;
        private final int numberOfColumns;
        private final int id;
        private final LocalDateTime rowFirsDateTime;

        public RowParams(final int width, final int height, final int numberOfColumns, final int id,
                         final LocalDateTime rowFirsDateTime) {
            this(height, numberOfColumns, id, rowFirsDateTime);
            this.width = width;
        }

        public RowParams(final int height, final int numberOfColumns, final int id,
                         final LocalDateTime rowFirsDateTime) {
            this.height = height;
            this.numberOfColumns = numberOfColumns;
            this.id = id;
            this.rowFirsDateTime = rowFirsDateTime;
        }

        public int getWidth() {
            return this.width;
        }

        public int getHeight() {
            return this.height;
        }

        public int getNumberOfColumns() {
            return this.numberOfColumns;
        }

        public int getId() {
            return this.id;
        }

        public LocalDateTime getRowFirsDateTime() {
            return this.rowFirsDateTime;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (!(o instanceof RowParams)) return false;
            final RowParams rowParams = (RowParams) o;
            return this.width == rowParams.width &&
                    this.height == rowParams.height &&
                    this.numberOfColumns == rowParams.numberOfColumns &&
                    this.id == rowParams.id &&
                    Objects.equals(this.rowFirsDateTime, rowParams.rowFirsDateTime);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.width, this.height, this.numberOfColumns, this.id,
                    this.rowFirsDateTime);
        }

        @Override
        public String toString() {
            return "RowParams{" +
                    "width=" + this.width +
                    ", height=" + this.height +
                    ", numberOfColumns=" + this.numberOfColumns +
                    ", id=" + this.id +
                    ", rowFirsDateTime=" + this.rowFirsDateTime +
                    '}';
        }
    }
}
