package com.example.radle.todo_calendar2.calendarView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.example.radle.todo_calendar2.R;
import com.example.radle.todo_calendar2.calendarView.tools.DateTimesCollector;
import com.example.radle.todo_calendar2.calendarView.tools.RowsMeasures;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class BoardListView extends ListView {
    private final CalendarRowAdapter adapter;
    private BoardParams params;

    public BoardListView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        setDividerHeight(0);
        this.adapter = new CalendarRowAdapter(context, R.layout.row_view, new ArrayList<>());
        setAdapter(this.adapter);

    }

    public void setParams(final BoardParams params) {
        this.params = params;
        this.adapter.setParams(params);
        try {
            this.adapter.addAll(
                    new DateTimesCollector().collectForWeekColumn(this.params.firstDateTime));
        } catch (final TimeNotAlignedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
        this.params.width = w;
        this.params.rowHeight = new RowsMeasures().measureRowHeight(this.params);
    }

    public static class BoardParams {
        private int width;
        private int rowHeight;
        private final int numberOfColumns;
        private final LocalDateTime firstDateTime;

        public BoardParams(final int width, final int rowHeight,
                           final int numberOfColumns,
                           final LocalDateTime firstDateTime) {
            this(numberOfColumns, firstDateTime);
            this.width = width;
            this.rowHeight = rowHeight;
        }

        public BoardParams(final int numberOfColumns,
                           final LocalDateTime firstDateTime) {
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
    }
}
