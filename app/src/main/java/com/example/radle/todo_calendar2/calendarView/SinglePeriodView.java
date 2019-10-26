package com.example.radle.todo_calendar2.calendarView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.radle.todo_calendar2.calendarView.tools.RowsMeasures;

import java.time.LocalDateTime;

public abstract class SinglePeriodView extends LinearLayout {

    private static final int NUMBER_OF_COLUMNS = 7;

    private static final float BOARD_WEIGHT = 0.08f;
    private static final float TOP_LABEL_WEIGHT = 0.92f;
    protected PeriodParams params;
    private final Paint rectPaint = new Paint();
    private final Paint textPaint = new Paint();

    public SinglePeriodView(final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        // TODO zrobic to bardziej elegancko
        //((Activity) getContext()).getWindowManager().getDefaultDisplay().getSize(this.outSize);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
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

    private void compose() {
        removeAllViews();
        addTopLabelRow();
        addBoardListView();
    }

    private void addTopLabelRow() {
        final TopLabelRow topLabelRow = new TopLabelRow(getContext(), null);
        topLabelRow.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, getTopLabelWeight()));
        topLabelRow.setParams(buildTopLabelParms());
        addView(topLabelRow);
    }

    private void addBoardListView() {
        final BoardListView boardListView = new BoardListView(getContext(), null);
        boardListView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, getBoardWeight()));
        boardListView.setParams(buildBoardParams());

        addView(boardListView);
    }

    private BoardListView.BoardParams buildBoardParams() {
        return new BoardListView.BoardParams(getSmallerScreenDimension(),
                new RowsMeasures().measureRowHeight(7),
                NUMBER_OF_COLUMNS, this.params.firstDateTime);
    }

    private TopLabelRow.RowParams buildTopLabelParms() {
        return new TopLabelRow.RowParams(NUMBER_OF_COLUMNS, this.params.firstDateTime);
    }

    public LocalDateTime getDateTime() {
        return this.params.firstDateTime;
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
        display.getSize(size);
        return Math.min(size.x, size.y);
    }
}
