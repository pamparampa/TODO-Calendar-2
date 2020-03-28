package com.example.radle.todo_calendar2.calendarView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

import com.example.radle.todo_calendar2.R;
import com.example.radle.todo_calendar2.calendarView.tools.DateTimesCollector;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Deprecated
public class BoardListView extends ListView {
    private final CalendarRowAdapter adapter;
    private BoardParams params;
    private OnHorizontalScrollListener onHorizontalScrollListener;

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
        this.onHorizontalScrollListener = onHorizontalScrollListener;
        this.adapter.setOnHorizontalScrollListener(onHorizontalScrollListener);
    }

    public void setParams(final BoardParams params) {
        this.params = params;
        this.adapter.setParams(params);
        try {
            this.adapter.addAll(
                    new DateTimesCollector().collectRowsForWeek(this.params.firstDateTime));
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

        public LocalDateTime getFirstDateTime() {
            return this.firstDateTime;
        }
    }

    @Override
    public boolean onTouchEvent(final MotionEvent ev) {
        if (this.onHorizontalScrollListener != null) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN)
                this.onHorizontalScrollListener.startScrolling(ev.getX());
            else if (ev.getAction() == MotionEvent.ACTION_UP)
                this.onHorizontalScrollListener.finishScrollingVertically();
        }
        return super.onTouchEvent(ev);
    }
}
