package com.example.radle.todo_calendar2.calendarView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.example.radle.todo_calendar2.R;
import com.example.radle.todo_calendar2.calendarView.tools.CalendarMeasure;
import com.example.radle.todo_calendar2.calendarView.tools.DateTimesCollector;

import java.time.LocalDateTime;

public class BoardListView extends ListView {
    private BoardParams params;

    public BoardListView(final Context context, final AttributeSet attrs) {
        super(context, attrs);

        try {
            setAdapter(new CalendarRowAdapter(context, R.layout.row_view, this.params,
                    DateTimesCollector.collectForBoardListView(this.params.firstDateTime)));
        } catch (final TimeNotAlignedException e) {
            e.printStackTrace();
        }
    }

    public void setParams(final BoardParams params) {
        this.params = params;
    }

    @Override
    protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
        this.params.width = w;
        this.params.rowHeight = new CalendarMeasure().measureRowHeight(this.params);
    }

    public static class BoardParams {
        private int width;
        private int rowHeight;
        private final int numberOfRows;
        private final int numberOfColumns;
        private final LocalDateTime firstDateTime;

        public BoardParams(final int width, final int rowHeight, final int numberOfRows,
                           final int numberOfColumns,
                           final LocalDateTime firstDateTime) {
            this(numberOfRows, numberOfColumns, firstDateTime);
            this.width = width;
            this.rowHeight = rowHeight;

        }

        public BoardParams(final int numberOfRows, final int numberOfColumns,
                           final LocalDateTime firstDateTime) {
            this.numberOfRows = numberOfRows;
            this.numberOfColumns = numberOfColumns;
            this.firstDateTime = firstDateTime;
        }

        public int getWidth() {
            return this.width;
        }

        public int getRowHeight() {
            return this.rowHeight;
        }

        public int getNumberOfColumns() {
            return this.numberOfColumns;
        }

        public LocalDateTime getFirstDateTime() {
            return this.firstDateTime;
        }
    }
}
