package com.example.radle.todo_calendar2.calendarView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.example.radle.todo_calendar2.R;
import com.example.radle.todo_calendar2.calendarView.tools.DateTimesCollector;

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

    @Override
    protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
        super.onSizeChanged(1080, h, oldw, oldh);
    }

    public void setOnHorizontalScrollListener(final OnHorizontalScrollListener onHorizontalScrollListener) {
        this.adapter.setOnHorizontalScrollListener(onHorizontalScrollListener);
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

    public static class BoardParams {
        private final int numberOfColumns;
        private final LocalDateTime firstDateTime;
        private final int rowHeight;
        private final int width;

        public BoardParams(final int width, final int rowHeight,
                           final int numberOfColumns,
                           final LocalDateTime firstDateTime) {
            this.width = width;
            this.numberOfColumns = numberOfColumns;
            this.firstDateTime = firstDateTime;
            this.rowHeight = rowHeight;
        }

        public int getNumberOfColumns() {
            return this.numberOfColumns;
        }

        public int getRowHeight() {
            return this.rowHeight;
        }

        public int getWidth() {
            return this.width;
        }
    }
}
