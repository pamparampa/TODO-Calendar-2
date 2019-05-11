package com.example.radle.todo_calendar2.calendarView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.time.LocalDateTime;

import androidx.annotation.Nullable;

public abstract class SinglePeriodView extends LinearLayout {

    private static final float BOARD_WEIGHT = 0.9f;
    private static final float TOP_LABEL_WEIGHT = 0.1f;
    protected PeriodParams params;

    public SinglePeriodView(final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
    }

    protected float getBoardWeight() {
        return BOARD_WEIGHT;
    }

    protected float getTopLabelWeight() {
        return TOP_LABEL_WEIGHT;
    }

    public static class PeriodParams {
        protected LocalDateTime firstDateTime;
    }
}
