package com.example.radle.todo_calendar2.calendarView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
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

    private RowParams params;
    private List<CalendarLabel> topLabelFields;
    private final Paint labelTextPaint = new Paint();

    public TopLabelRow(final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
        this.labelTextPaint.setAntiAlias(true);
    }

    public void setParams(final RowParams rowParams) {
        this.params = rowParams;
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        compose();
        this.topLabelFields.forEach(
                label -> canvas.drawText(
                        label.getText(), label.getTextX(), label.getTextY(), this.labelTextPaint));
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

    @Override
    protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.params.height = h;
        this.params.width = w;
    }

    private CalendarLabel getTopLabelField(final IdWithDataTime idWithDataTime) {
        return new CalendarRowElementsComposer().getTopLabel(this.params,
                idWithDataTime.getDateTime().toLocalDate());
    }

    public static class RowParams extends com.example.radle.todo_calendar2.calendarView.RowParams {
        public RowParams(final int numberOfColumns,
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
