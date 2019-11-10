package com.example.radle.todo_calendar2.activity;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.radle.todo_calendar2.R;
import com.example.radle.todo_calendar2.calendarView.SinglePeriodView;
import com.example.radle.todo_calendar2.calendarView.SingleWeekView;

import java.time.LocalDateTime;
import java.time.Month;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SingleWeekViewActivity extends Activity {

    @BindView(R.id.single_week_view)
    SingleWeekView singleWeekView;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_week_view_activity);
        ButterKnife.bind(this);
        this.singleWeekView.setParams(makeParams());
    }

    private SinglePeriodView.PeriodParams makeParams() {
        return new SinglePeriodView.PeriodParams(LocalDateTime.of(2019, Month.JUNE, 17, 0, 0, 0));
    }
}
