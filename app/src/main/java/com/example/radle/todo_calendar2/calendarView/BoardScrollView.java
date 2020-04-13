package com.example.radle.todo_calendar2.calendarView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.radle.todo_calendar2.calendarView.dto.CalendarEvent;
import com.example.radle.todo_calendar2.calendarView.dto.IdWithDataTime;
import com.example.radle.todo_calendar2.calendarView.tools.DateTimesCollector;
import com.example.radle.todo_calendar2.calendarView.tools.ParamsBuilder;
import com.example.radle.todo_calendar2.calendarView.tools.events.EventsComposer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BoardScrollView extends ScrollView {

    private final LinearLayout linearLayout;
    private BoardParams params;
    private OnHorizontalScrollListener onHorizontalScrollListener;
    private List<CalendarRowView> rowViews = new ArrayList<>(24);
    private EventsDao eventsDao;

    public BoardScrollView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        this.linearLayout = new LinearLayout(context, null);
    }

    public void setOnHorizontalScrollListener(final OnHorizontalScrollListener onHorizontalScrollListener) {
        this.onHorizontalScrollListener = onHorizontalScrollListener;
        for (final CalendarRowView rowView : this.rowViews) {
            rowView.setOnHorizontalScrollListener(onHorizontalScrollListener);
        }
    }

    public void setParams(final BoardParams params) {
        this.params = params;
        try {
            createRowViews();
            initLinearLayout();
            addView(this.linearLayout);
            addEvents();

        } catch (final TimeNotAlignedException e) {
            e.printStackTrace();
        }
    }

    private void addEvents() {
        final List<CalendarEvent> events = this.eventsDao.getEvents();
        try {
            final EventsComposer eventsComposer = new EventsComposer(this.params.firstDateTime);
        } catch (final TimeNotAlignedException e) {
            e.printStackTrace();
        }

    }

    private void initLinearLayout() {
        this.linearLayout.removeAllViews();
        for (final CalendarRowView rowView : this.rowViews) {
            this.linearLayout.addView(rowView);
        }
        this.linearLayout
                .setLayoutParams(
                        new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
        this.linearLayout.setOrientation(LinearLayout.VERTICAL);
    }

    private void createRowViews() throws TimeNotAlignedException {
        final List<IdWithDataTime> idWithDataTimes =
                new DateTimesCollector().collectRowsForWeek(this.params.getFirstDateTime());
        this.rowViews = new ArrayList<>(24);
        for (final IdWithDataTime idWithDataTime : idWithDataTimes) {
            final CalendarRowView rowView = initRowView(idWithDataTime);
            this.rowViews.add(rowView);
        }
    }

    private CalendarRowView initRowView(final IdWithDataTime rowIdWithFirstDateTime) {
        final CalendarRowView calendarRowView = new CalendarRowView(getContext(), null);
        calendarRowView.setParams(new ParamsBuilder().getRowParamsByBoardParams(this.params,
                rowIdWithFirstDateTime));
        calendarRowView.setOnHorizontalScrollListener(this.onHorizontalScrollListener);
        return calendarRowView;
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

        LocalDateTime getFirstDateTime() {
            return this.firstDateTime;
        }
    }
}
