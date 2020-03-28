package com.example.radle.todo_calendar2.calendarView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.radle.todo_calendar2.calendarView.dto.IdWithDataTime;
import com.example.radle.todo_calendar2.calendarView.tools.DateTimesCollector;
import com.example.radle.todo_calendar2.calendarView.tools.ParamsBuilder;

import java.util.ArrayList;
import java.util.List;

public class BoardScrollView extends ScrollView {

    private final LinearLayout linearLayout;
    private BoardListView.BoardParams params;
    private OnHorizontalScrollListener onHorizontalScrollListener;
    private List<CalendarRowView> rowViews = new ArrayList<>(24);

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

    public void setParams(final BoardListView.BoardParams params) {
        this.params = params;
        try {
            final List<IdWithDataTime> idWithDataTimes =
                    new DateTimesCollector().collectRowsForWeek(
                            this.params.getFirstDateTime());
            this.linearLayout.removeAllViews();
            this.rowViews = new ArrayList<>(24);
            for (final IdWithDataTime idWithDataTime : idWithDataTimes) {
                final CalendarRowView rowView = initRowView(idWithDataTime);
                this.linearLayout.addView(rowView);
                this.rowViews.add(rowView);
            }
            this.linearLayout
                    .setLayoutParams(
                            new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT));
            this.linearLayout.setOrientation(LinearLayout.VERTICAL);
            addView(this.linearLayout);

        } catch (final TimeNotAlignedException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onLayout(final boolean changed, final int l, final int t, final int r,
                            final int b) {
        super.onLayout(changed, l, t, r, b);
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
}
