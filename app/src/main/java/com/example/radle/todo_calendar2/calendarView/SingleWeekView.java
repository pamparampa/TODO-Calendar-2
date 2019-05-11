package com.example.radle.todo_calendar2.calendarView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

public class SingleWeekView extends SinglePeriodView {

    private static final int NUMBER_OF_COLUMNS = 7;

    public SingleWeekView(final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);

    }

    public void setParams(final PeriodParams params) {
        this.params = params;
    }

    @Override
    protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
        compose();
    }

    private void compose() {
        removeAllViews();
        addTopLabelRow();
        addBoardListView();
    }

    private void addBoardListView() {
        final BoardListView boardListView = new BoardListView(getContext(), null);
        boardListView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, getBoardWeight()));
        boardListView.setParams(buildBoardParams());

        addView(boardListView);
    }

    private BoardListView.BoardParams buildBoardParams() {
        return new BoardListView.BoardParams(NUMBER_OF_COLUMNS, this.params.firstDateTime);
    }

    private void addTopLabelRow() {
        final TopLabelRow topLabelRow = new TopLabelRow(getContext(), null);
        topLabelRow.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, getTopLabelWeight()));
        topLabelRow.setParams(buildTopLabelParms());
        addView(topLabelRow);
    }

    private TopLabelRow.RowParams buildTopLabelParms() {
        return new TopLabelRow.RowParams(NUMBER_OF_COLUMNS, this.params.firstDateTime);
    }

}
