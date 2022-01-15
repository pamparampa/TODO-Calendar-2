package com.example.radle.todo_calendar2.calendarView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.radle.todo_calendar2.dto.CalendarEvent;
import com.example.radle.todo_calendar2.calendarView.tools.RowsMeasures;
import com.example.radle.todo_calendar2.dto.CalendarSelection;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

public abstract class SinglePeriodView extends LinearLayout {

    private static final int NUMBER_OF_COLUMNS = 7;

    private static final float BOARD_WEIGHT = 0.08f;
    private static final float TOP_LABEL_WEIGHT = 0.92f;
    protected PeriodParams params;
    private BoardScrollView boardListView;
    private OnHorizontalScrollListener onHorizontalScrollListener;

    public SinglePeriodView(final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
    }

    protected float getBoardWeight() {
        return BOARD_WEIGHT;
    }

    protected float getTopLabelWeight() {
        return TOP_LABEL_WEIGHT;
    }

    public void setParams(final PeriodParams params) {
        this.params = params;
        compose();
    }

    public void setOnHorizontalScrollListener(final OnHorizontalScrollListener listener) {
        this.onHorizontalScrollListener = listener;
        if (this.boardListView != null)
            this.boardListView.setOnHorizontalScrollListener(this.onHorizontalScrollListener);
    }

    public void fillWithEvents(final List<CalendarEvent> events) {
        if (this.boardListView != null) {
            this.boardListView.fillWithEvents(events);
        }
    }

    private void compose() {
        removeAllViews();
        addTopLabelRow();
        this.boardListView = addBoardListView();
    }

    private void addTopLabelRow() {
        final TopLabelRow topLabelRow = new TopLabelRow(getContext(), null);
        topLabelRow.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, getTopLabelWeight()));
        topLabelRow.setParams(buildTopLabelParms());
        addView(topLabelRow);
    }

    private BoardScrollView addBoardListView() {
        final BoardScrollView boardListView = new
                BoardScrollView(getContext(), null);
        boardListView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, getBoardWeight()));
        boardListView.setParams(buildBoardParams());
        boardListView.setOnHorizontalScrollListener(this.onHorizontalScrollListener);

        addView(boardListView);
        return boardListView;
    }

    private BoardScrollView.BoardParams buildBoardParams() {
        final int smallerScreenDimension = getSmallerScreenDimension();
        return new BoardScrollView.BoardParams(smallerScreenDimension,
                new RowsMeasures().measureRowHeight(smallerScreenDimension, 7),
                NUMBER_OF_COLUMNS, this.params.firstDateTime);
    }

    private TopLabelRow.RowParams buildTopLabelParms() {
        final int smallerScreenDimension = getSmallerScreenDimension();
        return new TopLabelRow.RowParams(smallerScreenDimension,
                new RowsMeasures().measureRowHeight(smallerScreenDimension, 7),
                NUMBER_OF_COLUMNS, this.params.firstDateTime);
    }

    public LocalDateTime getDateTime() {
        return this.params.firstDateTime;
    }

    public int getCurrentVisiblePosition() {
        return this.boardListView.getScrollY();
    }

    public void scrollTo(final int position) {
        this.boardListView.scrollTo(0, position);
    }

    public void addEvents(final List<CalendarEvent> events) {
        if (this.boardListView != null) {
            this.boardListView.addEvents(events);
        }
    }

    public void handleClick(float x, float y, Consumer<CalendarEvent> postAction) {
        this.boardListView.handleClick(x, y, postAction);
    }

    public CalendarSelection getCurrentSelection() {
        return this.boardListView.getCurrentSelection();
    }

    public static class PeriodParams {
        LocalDateTime firstDateTime;

        public PeriodParams(final LocalDateTime firstDateTime) {
            this.firstDateTime = firstDateTime;
        }
    }

    private int getSmallerScreenDimension() {
        final Activity activity = (Activity) getContext();
        final Display display = activity.getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getRealSize(size);
        return Math.min(size.x, size.y);
    }


}
