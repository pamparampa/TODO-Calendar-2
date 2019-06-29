package com.example.radle.todo_calendar2.calendarView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.example.radle.todo_calendar2.calendarView.dto.CalendarLabel;
import com.example.radle.todo_calendar2.calendarView.dto.IdWithDataTime;
import com.example.radle.todo_calendar2.calendarView.tools.CalendarRowElementsComposer;
import com.example.radle.todo_calendar2.calendarView.tools.DateTimesCollector;

import java.time.LocalDateTime;
import java.util.List;

import androidx.annotation.Nullable;

import static java.util.stream.Collectors.toList;

public class TopLabelRow extends View {

    private final CalendarRowElementsComposer calendarRowElementsComposer =
            new CalendarRowElementsComposer();
    private RowParams params;
    private List<CalendarLabel> topLabelFields;
    private final TextPaint labelTextPaint = new TextPaint();
    private final Paint linePaint = new Paint();

    public TopLabelRow(final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
        this.labelTextPaint.setAntiAlias(true);
        this.linePaint.setStrokeWidth(5);
    }

    public void setParams(final RowParams rowParams) {
        this.params = rowParams;
    }

    @Override
    protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
        this.params.height = h;
        this.params.width = w;
        this.labelTextPaint.setTextSize(this.calendarRowElementsComposer.getTextSize(this.params));
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        compose();
        this.topLabelFields.forEach(
                label -> drawText(canvas, label));
        drawBottomLine(canvas);
    }

    private void compose() {
        try {
            this.topLabelFields =
                    new DateTimesCollector()
                            .collectForTopLabelRow(this.params.getRowFirsDateTime())
                            .stream()
                            .map(this::getTopLabelField)
                            .collect(toList());
        } catch (final TimeNotAlignedException e) {
            e.printStackTrace();
        }
    }

    private CalendarLabel getTopLabelField(final IdWithDataTime idWithDataTime) {
        return this.calendarRowElementsComposer.getTopLabel(this.params, idWithDataTime);
    }

    private void drawText(final Canvas canvas, final CalendarLabel label) {
        final StaticLayout textLayout = new StaticLayout(label.getText(), this.labelTextPaint,
                label.getFieldWidth(), Layout.Alignment.ALIGN_CENTER, 1.0f,
                0.0f,
                false);
        canvas.save();
        canvas.translate(label.getTextX(), label.getTextY());
        textLayout.draw(canvas);
        canvas.restore();
    }

    private void drawBottomLine(final Canvas canvas) {
        canvas.drawLine(0, this.params.height, this.params.width, this.params.height,
                this.linePaint);
    }

    public static class RowParams extends com.example.radle.todo_calendar2.calendarView.RowParams {
        RowParams(final int numberOfColumns,
                  final LocalDateTime firstDateTime) {
            this.numberOfColumns = numberOfColumns;
            this.rowFirsDateTime = firstDateTime;
        }

        public RowParams(final int width, final int height, final int numberOfColumns,
                         final LocalDateTime firstDateTime) {
            this(numberOfColumns, firstDateTime);
            this.width = width;
            this.height = height;
        }
    }
}